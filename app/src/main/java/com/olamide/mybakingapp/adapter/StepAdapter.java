package com.olamide.mybakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.bean.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {


    private Context context;
    private List<Step> steps;
    private Integer layoutToInflate;
    private final StepAdapter.StepAdapterOnClickListener stepOnClickListener;

    public StepAdapter(List<Step> steps, Context context, int layout, StepAdapter.StepAdapterOnClickListener stepOnClickListener){
        this.steps = steps;
        this.context = context;
        this.layoutToInflate = layout;
        this.stepOnClickListener = stepOnClickListener;
    }


    public void setStepList(List<Step> stepList){
        this.steps = stepList;
        notifyDataSetChanged();
    }


    public interface  StepAdapterOnClickListener {
        void onClickListener(Step step);
    }


    public  class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_stepNumber)
        TextView tStepNumber;

        @BindView(R.id.tv_shortDescription)
        TextView tShortDescription;


        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            stepOnClickListener.onClickListener(steps.get(adapterPosition));
        }
    }





    @NonNull
    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutToInflate, parent, false);
        return new StepAdapter.StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepAdapterViewHolder holder, int position) {

        Step step = steps.get(holder.getAdapterPosition());

        //Set values
        holder.tStepNumber.setText(step.getId().toString());
        holder.tShortDescription.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }




}
