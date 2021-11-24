package com.example.myapplication;

public class AddReviewAction {
    private Product product;
    private String email;
    private EditText editText;
    private RatingBar ratingBar;
    DatabaseReference reviewsReference;
    PopupWindow popupWindow;

    public AddReviewAction(Product product,String email,DatabaseReference reviewsReference) {
        this.product = product;
        this.email = email;
        this.reviewsReference = reviewsReference;
    }

    //PopupWindow display method
    public void showPopupWindow(final View view) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_review_layout, null);
        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        //Create a window with our parameters
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        editText = popupView.findViewById(R.id.et_review);
        ratingBar = popupView.findViewById(R.id.rating);

        Button buttonEdit = popupView.findViewById(R.id.btn_add_review);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview(v);
            }
        });

        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void addReview(View view){
        double rating = ratingBar.getRating();
        String reviewText = editText.getText().toString();
        if(reviewText.isEmpty()){
            Toast.makeText(view.getContext(), "Please Enter Provide Some Review Text", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseReference newRef = reviewsReference.push();
            Review review = new Review(email,product.name,reviewText,rating);
            newRef.setValue(review);
            Toast.makeText(view.getContext(), "Review is added for this product.", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        }
    }
}

}
