package quangnvph25768.poly.demothiandroidnc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListNewsAdapter extends ArrayAdapter<BaiBao> {
    public ListNewsAdapter(@NonNull Context context, int resource, List<BaiBao> list) {
        super(context, resource, list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.item_list_doc_bao, null);

        }

        BaiBao baiBao = getItem(position);
        if (baiBao != null) {

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(baiBao.getTitle());

        }
        return view;
    }
}
