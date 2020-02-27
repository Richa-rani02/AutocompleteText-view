package com.example.autocomplete;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends ArrayAdapter<String> {
    protected static final String TAG = "SuggestionAdapter";
    private List<String> suggestions = new ArrayList();

    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_dropdown_item_1line);
    }

    public int getCount() {
        return this.suggestions.size();
    }

    public String getItem(int index) {
        return (String) this.suggestions.get(index);
    }

    public Filter getFilter() {
        Filter myFilter = new Filter() {
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                JsonParse jp = new JsonParse();
                if (constraint != null) {
                    List<SuggestGetSet> new_suggestions = jp.getParseJsonWCF(constraint.toString());
                    SuggestionAdapter.this.suggestions.clear();

                    for (int i = 0; i < new_suggestions.size(); ++i) {
                        SuggestionAdapter.this.suggestions.add(((SuggestGetSet) new_suggestions.get(i)).getName());
                    }

                    filterResults.values = SuggestionAdapter.this.suggestions;
                    filterResults.count = SuggestionAdapter.this.suggestions.size();
                }

                return filterResults;
            }

            protected void publishResults(CharSequence contraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    SuggestionAdapter.this.notifyDataSetChanged();
                } else {
                    SuggestionAdapter.this.notifyDataSetInvalidated();
                }

            }
        };
        return myFilter;
    }
}
