package com.example.myschedulerapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Entities.Assessment;
import com.example.myschedulerapplication.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentsItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentsItemView = itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentView.class);
                    intent.putExtra("assessmentTitle", current.getTitle());
                    intent.putExtra("assessmentType", current.getType());
                    intent.putExtra("assessmentStartDate",current.getStartDate());
                    intent.putExtra("assessmentEndDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String assessmentTitle = current.getTitle();
            holder.assessmentsItemView.setText(assessmentTitle);
        }
        else {
            holder.assessmentsItemView.setText("No Assessment Added.Add an Assessment");
        }
    }

    /**
     *
     * @ determines the number of Items--> how many rows to put in the recyclerView.
     */

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }


    public void setAssessments(List<Assessment> allAssessements) {
        mAssessments=allAssessements;
        notifyDataSetChanged();
    }

}
