package cmput301f17t12.quirks.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f17t12.quirks.Activities.MainActivity;
import cmput301f17t12.quirks.Interfaces.Newsable;
import cmput301f17t12.quirks.R;

public class NewsItemAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Newsable> list = new ArrayList<>();
    private Context context;

    public NewsItemAdapter(ArrayList<Newsable> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.newsitem, null);
        }

        Newsable item = list.get(pos);
        //Handle TextView and display news header
        TextView listItemHeader = (TextView) view.findViewById(R.id.headerTxt);
        listItemHeader.setText(item.buildNewsHeader());

        //Handle TextView and display news description
        TextView listItemDescription = (TextView) view.findViewById(R.id.captionTxt);
        listItemDescription.setText(item.buildNewsDescription());

        //Handle TextView and display relative time span
        TextView listItemTimeSpan = (TextView) view.findViewById(R.id.timeTxt);
        listItemTimeSpan.setText(item.buildDate());

        ImageView listSymbol = (ImageView) view.findViewById(R.id.newsSymbol);
        if (item.getClass().getSimpleName().equals("Event")) {
            listSymbol.setImageResource(R.drawable.ic_facenews);
        } else if (item.getClass().getSimpleName().equals("Quirk")) {
            listSymbol.setImageResource(R.drawable.ic_writenews);
        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
//        ((MainActivity) context).updateSummary();
        super.notifyDataSetChanged();
    }
}

