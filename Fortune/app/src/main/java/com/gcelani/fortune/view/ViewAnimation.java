package com.gcelani.fortune.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * ViewAnimation
 */
public class ViewAnimation {

    /**
     * init
     * @param view view
     */
    public static void init(final View view) {
        view.setVisibility(View.GONE);
        view.setTranslationY(view.getHeight());
        view.setAlpha(0f);
    }

    /**
     * rotateFab
     * @param view view
     * @param rotate rotate
     * @return Rotation
     */
    public static boolean rotateFab(final View view, boolean rotate) {
        view.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).rotation(rotate ? 135f : 0f);

        return rotate;
    }

    /**
     * showIn
     * @param view view
     */
    public static void showIn(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.setTranslationY(view.getHeight());
        view.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).alpha(1f).start();
    }

    /**
     * showOut
     * @param view view
     */
    public static void showOut(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1f);
        view.setTranslationY(0);
        view.animate()
                .setDuration(200)
                .translationY(view.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f).start();
    }
}
