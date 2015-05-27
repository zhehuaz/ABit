package org.bitoo.abit.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;

import org.bitoo.abit.R;
import org.bitoo.abit.mission.image.Mission;
import org.bitoo.abit.utils.MissionSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    MissionSQLiteHelper sqLiteHelper;
    CardListView cardListView;
    CardArrayAdapter cardArrayAdapter;
    Button addButton;

    public static DetailedMissionActivityFragment newInstance(String param1, String param2) {
        DetailedMissionActivityFragment fragment = new DetailedMissionActivityFragment();
        return fragment;
    }

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
        addButton = (Button) getActivity().findViewById(R.id.bt_add);
        addButton.setBackgroundColor(Color.parseColor("#EE0000"));

        sqLiteHelper = new MissionSQLiteHelper(getActivity().getApplication());
        List<Mission> missions = sqLiteHelper.loadMissions();
        ArrayList<Card> cards = new ArrayList<>();
        cardListView = (CardListView) getActivity().findViewById(R.id.cdlv_missions);


        for(final Mission mission : missions) {
            ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();
            TextSupplementalAction shareAction = new TextSupplementalAction(getActivity(), R.id.tv_share);
            shareAction.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getActivity(), "SHARE", Toast.LENGTH_SHORT).show();
                }
            });
            TextSupplementalAction deleteAction = new TextSupplementalAction(getActivity(), R.id.tv_delete);
            deleteAction.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getActivity(), "DELETE", Toast.LENGTH_LONG).show();
                }
            });
            actions.add(shareAction);
            actions.add(deleteAction);

            MaterialLargeImageCard card =
                    MaterialLargeImageCard.with(getActivity())
                            .setTextOverImage(mission.getTitle())
                            .setTitle("This is a test")
                            .setSubTitle("This is a little test")
                            .useDrawableId(R.drawable.mario)
                            .setupSupplementalActions(R.layout.cd_mission_supplemental, actions)
                            .build();

            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Intent intent = new Intent(getActivity(), DetailedMissionActivity.class);
                    intent.putExtra("mission_id", mission.getId());
                    getActivity().startActivity(intent);
                }
            });
            cards.add(card);
        }
        cardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        cardListView.setAdapter(cardArrayAdapter);
    }
}
