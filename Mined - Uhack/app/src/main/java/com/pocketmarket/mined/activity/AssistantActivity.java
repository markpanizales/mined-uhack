package com.pocketmarket.mined.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;

import com.pocketmarket.mined.SingleMainFragmentActivity;
import com.pocketmarket.mined.adapter.GlobalMenuAdapter;
import com.pocketmarket.mined.fragments.AssistantFragment;

/**
 * Created by mark on 12/1/17.
 */

public class AssistantActivity extends SingleMainFragmentActivity {
    private final static String TAG = "AssistantActivity";

    @Override
    protected Fragment createFragment() {
        return new AssistantFragment();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Log.d(TAG, "Perform position...." + position);
                closeDrawerOnly();
                break;

            default: // assistant
                Log.d(TAG, "feed....");
                closeDrawerOnly();
                String roomId = null;
                String roomName = null;

                GlobalMenuAdapter.GlobalMenuItem item = ((GlobalMenuAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter()).getItem(position - 1);

                if (item != null) {
                    roomId = item.roomId;
                    roomName = item.label;
                }
                showMined(roomId, roomName);
                break;
        }
    }

    private void showMined(String roomId, String roomName) {
        Intent intent = new Intent(this, MinedActivity.class);
        intent.putExtra(ChatActivity.EXTRA_ROOM_NAME, roomName);
        intent.putExtra(ChatActivity.EXTRA_ROOM_ID, roomId);
        startActivity(intent);
    }
}
