##### ConstraintSet을 이용하여 동적으로 위치를 잡아주는 방법
```java

ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraintLayout);

ImageView imageView = new ImageView(ChooseOptionsActivity.this);
imageView.setImageResource(R.drawable.redlight);

layout.addView(imageView);

setContentView(layout);

// Get existing constraints into a ConstraintSet
ConstraintSet constraints = new ConstraintSet();
constraints.clone(layout);
// Define our ImageView and add it to layout
ImageView imageView = new ImageView(this);
imageView.setId(View.generateViewId());
imageView.setImageResource(R.drawable.redlight);
layout.addView(imageView);
// Now constrain the ImageView so it is centered on the screen.
// There is also a "center" method that can be used here.
constraints.constrainWidth(imageView.getId(), ConstraintSet.WRAP_CONTENT);
constraints.constrainHeight(imageView.getId(), ConstraintSet.WRAP_CONTENT);
constraints.center(imageView.getId(), ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
        0, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0, 0.5f);
constraints.center(imageView.getId(), ConstraintSet.PARENT_ID, ConstraintSet.TOP,
        0, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0, 0.5f);
constraints.applyTo(layout);
```
##### 여기서 배울 수 있는 것은 동적으로 view를 생성했어도 id를 생성하고 받아올 수 있다는 것!!
##### 그리고 내가 가진 layout을 받아온 후 ConstraintSet의 clone을 통해 matching을 시켜줄 수 있다는 것!!


##### 처음 시작할 때 layout 등 view의 크기를 측정하려고 하면 자꾸 0이 나온다.
##### 그 이유는 다음과 같다.
##### The view itself, has it's own life cycle which is basically as follows:
##### -Attached
##### -Measured
##### -Layout
##### -Draw

##### 해결방법
```java
LinearLayout layout = (LinearLayout)findViewById(R.id.YOUD VIEW ID);
ViewTreeObserver vto = layout.getViewTreeObserver();
vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                this.layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                this.layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        int width  = layout.getMeasuredWidth();
        int height = layout.getMeasuredHeight();

    }
});
```
