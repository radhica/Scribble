package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import modelObjects.Notes;
import noteapplication.scribble.com.scribble.R;

/**
 * Created by rsampath on 7/24/15.
 */
public class NotesAdapter extends ArrayAdapter<Notes> {

    public NotesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NotesAdapter(Context context, int resource, List<Notes> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_notes, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.notes_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.notes_description);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Notes notesObject = getItem(position);
        viewHolder.title.setText(notesObject.getTitle());
        viewHolder.description.setText(notesObject.getDescription());

        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView description;
    }

}

