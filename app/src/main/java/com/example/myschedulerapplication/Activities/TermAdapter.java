package com.example.myschedulerapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myschedulerapplication.Entities.Term;
import com.example.myschedulerapplication.R;

import java.util.List;


public class TermAdapter  extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termsItemView;
        private TermViewHolder(View itemView) {
            super(itemView);
            termsItemView = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, DetailedTermView.class);
                    intent.putExtra("termName", current.getTermTitle());
                    intent.putExtra("termStart", current.getStartDate());
                    intent.putExtra("termEnd", current.getEndDate());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.terms_list_item, parent, false);
        return new TermViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term currTerm = mTerms.get(position);
            String termName = currTerm.getTermTitle();
            holder.termsItemView.setText(termName);
        }

        else {
            holder.termsItemView.setText("No Term Added.Add a Term");
        }

    }
    /**
     *
     * @return the number of items,since this method determines how many rows to put in a
     * recyclerview and it cannot be zero otherwise nothing will display in the recyclerview.
     */
    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> allTerms) {
        mTerms=allTerms;
        notifyDataSetChanged();

    }

}





