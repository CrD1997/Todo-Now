package com.example.macbook.umlproject;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder>{

    private List<Thing> mThingList;

    //内部类：放置CheckBox与TextView的容器
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mThingCheckBox;
        TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mThingCheckBox = (CheckBox) view.findViewById(R.id.thing_checkBox);
            mTextView=(TextView)view.findViewById(R.id.thing_date);
        }
    }

    public ThingAdapter(List<Thing> thingList) {
        mThingList=thingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        /*
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked image " + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        */
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thing thing = mThingList.get(position);
        holder.mThingCheckBox.setText(thing.getName());
        holder.mThingCheckBox.setChecked(thing.getIfDone());
        holder.mTextView.setText(thing.getDate());
    }

    @Override
    public int getItemCount() {
        return mThingList.size();
    }

}
