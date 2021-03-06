package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.ArrayList;
import java.util.List;

import modelObjects.Arts;
import noteapplication.scribble.com.scribble.R;

/**
 * Created by rsampath on 7/24/15.
 */
public class ArtsAdapter extends ArrayAdapter<Arts> implements UndoAdapter{
    ArrayList<Arts> arts = new ArrayList<>();
    ArrayList<Arts> filteredList = new ArrayList<>();
    private Context context;


    public ArtsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ArtsAdapter(Context context, int resource, List<Arts> items) {
        super(context, resource, items);
        this.arts = (ArrayList<Arts>) items;
        this.filteredList = (ArrayList<Arts>) items;
        this.context = context;

        getFilter();
    }

    @Override
    public int getCount() {
        return arts.size();
    }


    @Override
    public Arts getItem(int position) {
        return arts.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        arts.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_arts, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.art_title_list_item);
            viewHolder.lastModified = (TextView) convertView.findViewById(R.id.item_art_last_modified);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Arts artsObject = getItem(position);
        viewHolder.title.setText(artsObject.getTitle());
        viewHolder.lastModified.setText(artsObject.getLastModified());
        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView lastModified;
    }

    @NonNull
    @Override
    public View getUndoView(int i, @Nullable View v, @NonNull ViewGroup viewGroup) {
        View view = v;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.undo_row, viewGroup, false);
        }
        return view;
    }

    @NonNull
    @Override
    public View getUndoClickView(@NonNull View view) {
        return view.findViewById(R.id.undo_row_undobutton);
    }

}

