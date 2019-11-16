package com.openclassrooms.realestatemanager.ui.adapters.settings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RealEstateManagerAPIService service = DI.getService();
    private Context context;
    private static String click;

    public SettingsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType== 0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_type_layout,parent,false);
            viewHolder = new ViewHolder(view);
        }

        if(viewType== 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_type_layout,parent,false);
            viewHolder = new ViewHolder(view);
        }


        if (viewType == 2)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_name_layout,parent,false);
            viewHolder= new UserNameViewHolder(view);
        }

        if (viewType == 3)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_image_layout,parent,false);
            viewHolder= new ImageViewHolder(view);
        }

        if (viewType == 4)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_image_layout,parent,false);
            viewHolder= new ImageViewHolder(view);
        }


        if (viewType == 5){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_account_layout,parent,false);
            viewHolder= new DeleteTypeViewHolder(view);
        }

        if (viewType == 6){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_account_layout,parent,false);
            viewHolder= new DeleteTypeViewHolder(view);
        }

        if (viewType == 7){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_account_layout,parent,false);
            viewHolder= new DeleteTypeViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        if (holderView.getItemViewType() == 0) {
            ViewHolder holder = (ViewHolder) holderView;
            getSwitchListener(holder);
        } else  if (holderView.getItemViewType() == 1) {
            ViewHolder holder = (ViewHolder) holderView;
            holder.titleText.setText("Unit Measure");
            holder.choice1Text.setText("sq");
            holder.choice2Text.setText("m");
            getMeasureListener(holder);
        }  else if (holderView.getItemViewType() == 2){
            UserNameViewHolder userNameViewHolder = (UserNameViewHolder) holderView;
            userNameViewHolder.editText.setHint(service.getUser().getName());
            getUserNameTextListener(userNameViewHolder);
        } else if (holderView.getItemViewType() == 3){
            ImageViewHolder imageViewHolder = (ImageViewHolder) holderView;
            imageViewHolder.textView.setText("User image");

            if (service.getPreferences().getUserPhoto() != null){
                Glide.with(context).load(Uri.parse(service.getPreferences().getUserPhoto())).apply(RequestOptions.circleCropTransform()).into(imageViewHolder.imageView);
            } else {
                Glide.with(context).load(service.getUser().getPhotoUri()).apply(RequestOptions.circleCropTransform()).into(imageViewHolder.imageView);
                getImageUserListener(imageViewHolder);
            }
        } else if (holderView.getItemViewType() == 4){
            ImageViewHolder imageViewHolder = (ImageViewHolder) holderView;
            if (service.getPreferences().getMenuImage() != null){
                Glide.with(context).load(Uri.parse(service.getPreferences().getMenuImage())).into(imageViewHolder.imageView);
            } else {
                imageViewHolder.imageView.setImageResource(R.drawable.main_image);
            }
            getMenuImageListener(imageViewHolder);
        } else if (holderView.getItemViewType() == 5){
            DeleteTypeViewHolder deleteTypeViewHolder = (DeleteTypeViewHolder) holderView;
            deleteTypeViewHolder.button.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            deleteTypeViewHolder.button.setText("Default settings");
            deleteTypeViewHolder.button.setTextColor(context.getResources().getColor(android.R.color.black));
            getButtonDefaultListener(deleteTypeViewHolder);
        } else if (holderView.getItemViewType() == 6){
            DeleteTypeViewHolder deleteTypeViewHolder = (DeleteTypeViewHolder) holderView;
            getButtonDeleteListener(deleteTypeViewHolder);
        } else if (holderView.getItemViewType() == 7){
            DeleteTypeViewHolder deleteTypeViewHolder = (DeleteTypeViewHolder) holderView;
            deleteTypeViewHolder.button.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            deleteTypeViewHolder.button.setText("Confirm changes");
            deleteTypeViewHolder.button.setTextColor(context.getResources().getColor(android.R.color.black));
            getConfirmChanges(deleteTypeViewHolder);
        }

    }

    public void getSwitchListener(ViewHolder holder){
        if (service.getPreferences().getMonetarySystem().equals("€")){
            holder.monetarySwitch.setChecked(true);
        } else {
            holder.monetarySwitch.setChecked(false);
        }

        holder.monetarySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    service.getPreferences().setMonetarySystem("€");
                }else {
                    service.getPreferences().setMonetarySystem("$");
                }
            }
        });
    }

    public void getMeasureListener(ViewHolder holder){
        if (service.getPreferences().getMeasureUnity().equals("m")){
            holder.monetarySwitch.setChecked(true);
        } else {
            holder.monetarySwitch.setChecked(false);
        }

        holder.monetarySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    service.getPreferences().setMeasureUnity("m");
                }else {
                    service.getPreferences().setMeasureUnity("sq");
                }
            }
        });
    }

    private void getUserNameTextListener(UserNameViewHolder holder){
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != "") {
                    service.getPreferences().setUserName(editable.toString());
                }
            }
        });
    }

    private void getImageUserListener(final ImageViewHolder holder){
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = "User";
                showDialogAddNewPhoto(holder);
            }
        });
    }

    private void getMenuImageListener(final ImageViewHolder holder){
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = "Menu";
                showDialogAddNewPhoto(holder);
            }
        });
    }

    private void getButtonDefaultListener(DeleteTypeViewHolder holder){
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.getPreferences().setUserName(service.getUser().getName());
                service.getPreferences().setMonetarySystem("€");
                service.getPreferences().setMenuImage(null);
                service.getPreferences().setUserPhoto(null);
                notifyDataSetChanged();
            }
        });
    }

    private void getButtonDeleteListener(DeleteTypeViewHolder holder){
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getConfirmChanges(final DeleteTypeViewHolder holder){
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveToDatabase database = SaveToDatabase.getInstance(holder.itemView.getContext());
                database.preferencesDao().insertPreferences(service.getPreferences());
                Intent intent = new Intent(context, SecondActivity.class);
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
        }
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public SwitchCompat monetarySwitch;
        public TextView titleText;
        public TextView choice1Text;
        public TextView choice2Text;


        public ViewHolder(View itemView) {
            super(itemView);

            monetarySwitch = itemView.findViewById(R.id.switch_system);
            titleText = itemView.findViewById(R.id.money_text);
            choice1Text = itemView.findViewById(R.id.dollar);
            choice2Text = itemView.findViewById(R.id.euro);

        }
    }
    static class ImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_header);
            textView = itemView.findViewById(R.id.text_header_image);
        }
    }

    static class DeleteTypeViewHolder extends RecyclerView.ViewHolder{

        private Button button;

        public DeleteTypeViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.delete_account_btn);
        }
    }
    static class UserNameViewHolder extends RecyclerView.ViewHolder{

        private TextView txtView;
        private EditText editText;

        public UserNameViewHolder(View itemView) {
            super(itemView);

            txtView = itemView.findViewById(R.id.username_title);
            editText = itemView.findViewById(R.id.user_name_edit_text);
        }
    }

    private void showDialogAddNewPhoto(ImageViewHolder viewHolder){
        context = viewHolder.itemView.getContext().getApplicationContext();
        AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(viewHolder.itemView.getContext());
        notifyNewPhoto.setCancelable(true);
        notifyNewPhoto.setTitle("Add a new photo");
        notifyNewPhoto.setMessage("Where would you like to take it ?");
        notifyNewPhoto.setIcon(R.drawable.ic_add_blue_24dp);

        notifyNewPhoto.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    service.getActivity().startActivityForResult(intent, 100);
                } else {
                    ActivityCompat.requestPermissions(service.getActivity(), new String[]{Manifest.permission.CAMERA}, 105);
                }
            }
        });

        notifyNewPhoto.setNegativeButton("Files", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    service.getActivity().startActivityForResult(intent, 90);
                } else {
                    ActivityCompat.requestPermissions(service.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
                }
            }
        });

        notifyNewPhoto.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = notifyNewPhoto.create();
        alert.getWindow().setGravity(Gravity.BOTTOM);
        alert.show();
    }

    public static String getImageTypeClick(){
        return click;
    }


}
