package com.gcelani.fortune.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gcelani.fortune.R;
import com.gcelani.fortune.model.Account;
import com.gcelani.fortune.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * AccountsListAdapter
 */
public class AccountsListAdapter implements ListAdapter {

    /** Context */
    private WeakReference<Context> mContext;

    /** Accounts */
    private List<Account> mAccounts;

    /**
     * AccountsListAdapter
     * @param context context
     * @param accounts accounts
     */
    public AccountsListAdapter(Context context, List<Account> accounts) {
        this.mContext = new WeakReference<>(context);
        this.mAccounts = accounts;
    }

    /**
     * getView
     * @param position position
     * @param convertView convertView
     * @param parent parent
     * @return Convert View
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = mAccounts.get(position);

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext.get());
            convertView = layoutInflater.inflate(R.layout.list_row_accounts, null);
            convertView.setOnClickListener(onItemClickListener);

            ImageView iconImageView = convertView.findViewById(R.id.icon);
            TextView accountNameTextView = convertView.findViewById(R.id.account_name);
            TextView accountTypeTextView = convertView.findViewById(R.id.account_type);
            TextView accountBalanceTextView = convertView.findViewById(R.id.account_balance);

            iconImageView.setImageDrawable(mContext.get().getResources().getDrawable(Utils.getIconFromAccountType(mContext.get(), account.type)));
            accountNameTextView.setText(account.name);
            accountTypeTextView.setText(account.type);
            accountBalanceTextView.setText(Utils.prettyMoneyFormat(mContext.get(), account.balance));

            int newColor = account.positiveBalance ? R.color.green : R.color.red;
            iconImageView.setColorFilter(mContext.get().getResources().getColor(newColor));
            accountBalanceTextView.setTextColor(mContext.get().getResources().getColor(newColor));

            convertView.setTag(account);
        }

        return convertView;
    }

    /**
     * onItemClickListener
     */
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: Call AccountActivity in edit mode
            System.out.println(v.getTag());
        }
    };

    /**
     * areAllItemsEnabled
     * @return false
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     * isEnabled
     * @param position position
     * @return true
     */
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    /**
     * registerDataSetObserver
     * @param dataSetObserver dataSetObserver
     */
    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    /**
     * unregisterDataSetObserver
     * @param dataSetObserver dataSetObserver
     */
    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    /**
     * getCount
     * @return List Size
     */
    @Override
    public int getCount() {
        return mAccounts.size();
    }

    /**
     * getItem
     * @param position position
     * @return Position
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * getItemId
     * @param position position
     * @return Object ID
     */
    @Override
    public long getItemId(int position) {
        return mAccounts.get(position).id;
    }

    /**
     * hasStableIds
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * getItemViewType
     * @param position position
     * @return position
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * getViewTypeCount
     * @return List Size
     */
    @Override
    public int getViewTypeCount() {
        return mAccounts.size();
    }

    /**
     * isEmpty
     * @return List isEmpty
     */
    @Override
    public boolean isEmpty() {
        return mAccounts.isEmpty();
    }
}