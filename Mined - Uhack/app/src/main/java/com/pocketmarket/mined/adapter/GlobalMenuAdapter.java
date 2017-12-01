package com.pocketmarket.mined.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketmarket.mined.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 12/1/17.
 */

public class GlobalMenuAdapter extends ArrayAdapter<GlobalMenuAdapter.GlobalMenuItem> {
    private static final String TAG = "GlobalMenuAdapter";
    private static final int TYPE_MENU_ITEM = 0;
    private static final int TYPE_DIVIDER = 1;

    private final LayoutInflater inflater;
    private final List<GlobalMenuItem> menuItems = new ArrayList<>();
//    private int mChannelId;

//    private String mAccessToken;

    public GlobalMenuAdapter(Context context, Activity activity, int channelId) {
        super(context, 0);
        this.inflater = LayoutInflater.from(context);

//        mChannelId = channelId;

//        // get the shared preferences user value
//        mAccessToken = Utils.getAccessToken(activity);
//
//        setupRooms();
    }

//    private void setupRooms() {
//        Log.d(TAG, "setupRooms...");
//        new ChannelRoomsTask().execute(getRooms());
//    }
//
//    /**
//     * The method for create url
//     *
//     * @return
//     */
//    private String getRooms() {
//        return AppApi.URL_NAME + AppApi.CHANNEL_ROOMS + mAccessToken + CHANNEL_ROOMS_ID + mChannelId;
//    }

//    private class ChannelRoomsTask extends AsyncTask<String, Void, ArrayList<ChatChannelDTO>> {
//
//        public ChannelRoomsTask() {
//
//        }
//
//        @Override
//        protected ArrayList<ChatChannelDTO> doInBackground(String... url) {
//            Log.i(TAG, "URL: " + url[0]);
//            return new ChannelGetFetchr().fetchItems(url[0]);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<ChatChannelDTO> chatChannelList) {
//            Log.i(TAG, "onPostExecute createChannelDTO: " + chatChannelList);
//
//            if (chatChannelList == null)
//                return;
//
//            Log.d(TAG, "Size: " + chatChannelList.size());
//
//            setupMenuItems(chatChannelList);
//
//        }
//    }
//
//    private void setupMenuItems(ArrayList<ChatChannelDTO> chatChannelList) {
//
//        for (ChatChannelDTO chatChannel : chatChannelList) {
//            menuItems.add(new GlobalMenuItem(0, chatChannel.getName()).setRoomId(String.valueOf(chatChannel.getId())));
//        }
//
//        notifyDataSetChanged();
//
//    }


    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GlobalMenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return menuItems.get(position).isDivider ? TYPE_DIVIDER : TYPE_MENU_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_MENU_ITEM) {
            MenuItemViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_global_menu, parent, false);
                holder = new MenuItemViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MenuItemViewHolder) convertView.getTag();
            }

            GlobalMenuItem item = getItem(position);
            holder.tvLabel.setText(item.label);
            holder.ivIcon.setImageResource(item.iconResId);
            holder.ivIcon.setVisibility(item.iconResId == 0 ? View.GONE : View.VISIBLE);

            return convertView;

        } else {
            return inflater.inflate(R.layout.item_menu_divider, parent, false);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return !menuItems.get(position).isDivider;
    }

    public static class MenuItemViewHolder {
        ImageView ivIcon;
        TextView tvLabel;

        public MenuItemViewHolder(View view) {
            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);

            tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        }
    }

    public static class GlobalMenuItem {
        public int iconResId;
        public String label;
        public boolean isDivider;
        public String roomId;

        private GlobalMenuItem() {

        }

        public GlobalMenuItem(int iconResId, String label) {
            this.iconResId = iconResId;
            this.label = label;
            this.isDivider = false;
        }

        public static GlobalMenuItem dividerMenuItem() {
            GlobalMenuItem globalMenuItem = new GlobalMenuItem();
            globalMenuItem.isDivider = true;
            return globalMenuItem;
        }

        public GlobalMenuItem setRoomId(String roomId) {
            this.roomId = roomId;
            return this;
        }
    }

}
