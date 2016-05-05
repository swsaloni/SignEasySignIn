package example.com.signeasy.model;

import example.com.signeasy.R;

/**
 * Created by Saloni on 5/5/2016.
 */
public enum CustomPagerEnum
{
    Step1(R.layout.onboarding_1,R.string.clear),
    Step2(R.layout.onboarding_2,R.string.na),
    Step3(R.layout.onboarding_3,R.string.partlycloudy),
    Step4(R.layout.onboarding_4,R.string.sun);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum( int layoutResId,int titleResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
