package org.bitoo.abit.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.style.LineHeightSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.utils.MissionSQLiteHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.cards.topcolored.TopColoredCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.dismissanimation.SwipeDismissAnimation;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Card> cards;
    private MissionSQLiteHelper sqLiteHelper;
    private CardArrayAdapter cardArrayAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button addButton = (Button) getActivity().findViewById(R.id.bt_add);
        addButton.setOnClickListener(this);

        sqLiteHelper = new MissionSQLiteHelper(getActivity().getApplication());
        List<Mission> missions = sqLiteHelper.loadMissions();
        CardListView cardListView = (CardListView) getActivity().findViewById(R.id.cdlv_missions);

        // Show all missions
        cards = new ArrayList<Card>();
        for(Mission mission : missions) {
            cards.add(createCard(mission));
        }
        cardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(cardArrayAdapter);

    }

    @Override
    public void onClick(View view) {
        byte[] progress = new byte[50];
        for(int i = 0;i < 50;i ++){
            if(i % 30 != 0)
                progress[i] = ~0;
        }
        Mission mission = null;
        try {
            mission = new Mission(getActivity(),
                    1, "Test" + System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    "mario.xml",
                    progress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sqLiteHelper.addMission(mission);
        cardArrayAdapter.add(createCard(mission));
        cardArrayAdapter.notifyDataSetChanged();
    }

    private Card createCard(final Mission mission) {

        TopColoredCard card = TopColoredCard.with(getActivity())
                .setColorResId(mission.getId() % 2 == 0 ? R.color.material_deep_teal_500 : R.color.material_blue_grey_800)
                .setTitleOverColor(mission.getTitle())
                .setupSubLayoutId(R.layout.cd_mission_custom_layout)
                .setupInnerElements(new TopColoredCard.OnSetupInnerElements() {
                    @Override
                    public void setupInnerViewElementsSecondHalf(View view) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_custom_layout);
                        if(tv != null) {
                            tv.setText("12天");
                        }
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_custom_layout);
                        if(iv != null) {
                            iv.setImageResource(R.drawable.mario);
                        }
                    }
                })
                .build();
        card.setId(mission.getId() + "");

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(getActivity(), DetailedMissionActivity.class);
                intent.putExtra(MainActivity.MISSION_ID, mission.getId());
                getActivity().startActivity(intent);
            }
        });
        return card;
    }
}
