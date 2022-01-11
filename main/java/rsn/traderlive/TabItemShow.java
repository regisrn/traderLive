package rsn.traderlive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class TabItemShow extends RecyclerView.Adapter<TabItemShow.ViewHolder> {

    private final List<JogoModel> mValues;


    public TabItemShow(List<JogoModel> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tab_item_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mKey.setText(mValues.get(position).getJogo());
        holder.mValue.setText(mValues.get(position).getEntrada());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mKey;
        public final TextView mValue;
        public JogoModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mKey = (TextView) view.findViewById(R.id.jogo);
            mValue = (TextView) view.findViewById(R.id.entrada);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mValue.getText() + "'";
        }
    }
}

