package com.tuodanhuashu.app.course.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.presenter.AudioPlayPresenter;
import com.tuodanhuashu.app.course.ui.DialogFragmentDataCallback;

public class CommentDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText etComment;
    private Button btnSend;
    private InputMethodManager inputMethodManager;
    private DialogFragmentDataCallback dataCallback;
    private TextView tvMax;
    private TextView tvCurrent;
    private String content;
    private int mMaxCount = 140;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CommentDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comment_dialog_fragment_layout);
        dialog.setCanceledOnTouchOutside(true);



        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        etComment = dialog.findViewById(R.id.et_comment_dialgo);
        btnSend = dialog.findViewById(R.id.btn_comment_dialog_send);

        tvCurrent = dialog.findViewById(R.id.tv_comment_current);
        tvMax = dialog.findViewById(R.id.tv_comment_max);
        btnSend.setBackground(getResources().getDrawable(R.drawable.shape_button_send_unclickable));
        tvMax.setText("/"+mMaxCount);
        dataCallback = (DialogFragmentDataCallback) getActivity();
        setSoftKeyboard();

        etComment.addTextChangedListener(mTextWatcher);

        btnSend.setOnClickListener(this);

        return dialog;
    }

    private void setSoftKeyboard() {
        etComment.setFocusable(true);
        etComment.setFocusableInTouchMode(true);
        etComment.requestFocus();

        etComment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(etComment, 0)) {
                        etComment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            content = s.toString();
            tvCurrent.setText(s.length()+"");
            if (s.length()>mMaxCount){
                etComment.setText(content.substring(0,mMaxCount));
                etComment.setSelection(mMaxCount);
                Toast.makeText(getActivity(),"字数超过限制",Toast.LENGTH_SHORT).show();
                btnSend.setEnabled(false);
                btnSend.setBackground(getResources().getDrawable(R.drawable.shape_button_send_unclickable));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0 && temp.length() <= mMaxCount) {
                btnSend.setEnabled(true);
                btnSend.setClickable(true);
                btnSend.setBackground(getResources().getDrawable(R.drawable.shape_button_send_clickable));
            } else {
                btnSend.setEnabled(false);
                btnSend.setBackground(getResources().getDrawable(R.drawable.shape_button_send_unclickable));
            }
        }
    };


    @Override
    public void onClick(View v) {

        dataCallback.setCommentText(etComment.getText().toString());
        etComment.setText("");
        dismiss();
    }
}
