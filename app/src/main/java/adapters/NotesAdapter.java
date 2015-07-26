package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
    ArrayList<Notes> filteredList = new ArrayList<>();
    private NoteFilter noteFilter;

    public NotesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NotesAdapter(Context context, int resource, List<Notes> items) {
        super(context, resource, items);
        this.notes = (ArrayList<Notes>) items;
        this.filteredList = (ArrayList<Notes>) items;
        getFilter();
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
    public Filter getFilter() {
        if (noteFilter == null) {
            noteFilter = new NoteFilter();
        }


        return noteFilter;
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

    private class NoteFilter extends Filter {


        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Notes> tempList = new ArrayList<Notes>();


                // search content in friend list
                for (Notes user : notes) {
                    if (user.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }


                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = notes.size();
                filterResults.values = notes;
            }


            return filterResults;
        }


        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Notes>) results.values;
            notifyDataSetChanged();
        }
    }



    }

