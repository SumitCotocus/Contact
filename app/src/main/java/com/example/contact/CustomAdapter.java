package com.example.contact;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    Controllerdb controldb;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Organization = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Number = new ArrayList<String>();
    //    private ArrayList<Model> model_array = new ArrayList<Model>();
    public CustomAdapter(Context  context,ArrayList<String> id ,ArrayList<String> Organization,ArrayList<String> Name, ArrayList<String> Number)
    {
        this.mContext = context;
        this.Id=id;
        this.Organization = Organization;
        this.Name = Name;
        this.Number= Number;
//        for(int i=0; i< this.Name.size(); i++) {
////                Model m = new Model(this.Organization[i], this.Name[i], this.Number[i]);
//            if(Organization[i] != null) {
//
//            }
//           String or = this.Organization[i];
//            Model m = new Model(this.Organization[i], this.Name[i], this.Number[i]);
//
//            model_array.add();
//        }
    }
    @Override
    public int getCount() {
        return Name.size();
    }
    @Override
    public Model getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        controldb =new Controllerdb(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_main, null);
            holder = new viewHolder();

            holder.organization= (TextView) convertView.findViewById(R.id.tvorganization);
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.number= (TextView) convertView.findViewById(R.id.tvnumber);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.organization.setText(Organization.get(position));
        holder.name.setText(Name.get(position));
        holder.number.setText(Number.get(position));
        return convertView;
    }
    public class viewHolder {

        TextView organization;
        TextView name;
        TextView number;
    }

}