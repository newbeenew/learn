package d.ql.account;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ql on 16-4-12.
 */
public class FragmentTab extends Fragment{

    private View mViewContent;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        if (mViewContent == null){
            mViewContent = inflater.inflate(R.layout.fragment_main,  null);
        }

        ViewGroup parent  = (ViewGroup)mViewContent.getParent();
        if(null != parent){
            parent.removeView(mViewContent);
        }


        return mViewContent;
    }

    public void onDestroyView(){
        super.onDestroyView();
    }
}
