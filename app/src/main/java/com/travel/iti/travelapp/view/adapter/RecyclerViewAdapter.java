package com.travel.iti.travelapp.view.adapter;

//public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder> {
//
//    private List <Packages> packages ;
//    private Context context ;
//
//    public RecyclerViewAdapter(List<Packages> packages, Context context) {
//        this.packages = packages;
//        this.context = context;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cities_item , parent,false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//        holder.city.setText(packages.get(position).getTravelTo());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return packages.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView city ;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            city = itemView.findViewById(R.id.edit_text_city);
//
//        }
//    }
//}
