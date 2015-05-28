package org.bitoo.abit.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private final ArrayList<Card> cards = new ArrayList<>();
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

        // Init actions


        // Show all missions
        for(Mission mission : missions) {
            createCard(mission);
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
        createCard(mission);
        cardArrayAdapter.notifyDataSetChanged();
    }

    private Card createCard(final Mission mission) {
        ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();
        IconSupplementalAction shareAction = new IconSupplementalAction(getActivity(), R.id.tv_share);
        shareAction.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity(), "SHARE", Toast.LENGTH_SHORT).show();
            }
        });
        IconSupplementalAction deleteAction = new IconSupplementalAction(getActivity(), R.id.tv_delete);
        deleteAction.setOnActionClickListener(new IconSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(final Card card, View view) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("确定不再坚持 " + card.getTitle() + " 了吗？")
                        .setPositiveButton("不再坚持！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sqLiteHelper.deleteMission(getActivity().getApplication(),
                                        Long.parseLong(card.getId()));
                                if(!cards.contains(card))
                                    Toast.makeText(getActivity(), "Can't find this card", Toast.LENGTH_SHORT).show();
                                cards.remove(card);
                                cardArrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("还没想好", null)
                        .create()
                        .show();
            }
        });
        actions.add(shareAction);
        actions.add(deleteAction);

        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(getActivity())
                        .setTextOverImage(mission.getTitle())
                        .setTitle(mission.getTitle())
                        .useDrawableId(R.drawable.bg_cardtest)
                        .setupSupplementalActions(R.layout.cd_mission_supplemental, actions)
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
        cards.add(card);
        return card;
    }
}
