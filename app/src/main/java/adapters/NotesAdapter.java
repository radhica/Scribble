package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import modelObjects.Notes;
import noteapplication.scribble.com.scribble.R;

/**
 * Created by rsampath on 7/24/15.
 */
public class NotesAdapter extends ArrayAdapter<Notes> {
    ArrayList<Notes> notes = new ArrayList<>();

    public NotesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NotesAdapter(Context context, int resource, List<Notes> items) {
        super(context, resource, items);
        this.notes = (ArrayList<Notes>) items;
    }

    @Override
    public int getCount() {
        return notes.size();
    }


    @Override
    public Notes getItem(int position) {
        return notes.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        notes.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_notes, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_notes_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.item_notes_description);
            viewHolder.lastModified = (TextView) convertView.findViewById(R.id.item_notes_last_modified);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Notes notesObject = getItem(position);
        viewHolder.title.setText(notesObject.getTitle());

        String firstLine = notesObject.getDescription();
        String lines[] = firstLine.split("\\r?\\n");
        viewHolder.description.setText(lines[0]);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        viewHolder.lastModified.setText(dateString);

        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView description;
        public TextView lastModified;
    }

}

