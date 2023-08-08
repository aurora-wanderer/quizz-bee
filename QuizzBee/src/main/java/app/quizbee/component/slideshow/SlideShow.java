package app.quizbee.component.slideshow;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public final class SlideShow extends JLayeredPane {

    private final JPanel panel;
    private final Pagination pagination;
    private Animator animator;
    private TimingTarget target;
    private Timer timer;
    private int delay;
    private final MigLayout layout;
    private Component componentShow;
    private Component componentOut;
    private int currentIndex;
    private boolean next;
    private boolean autoNext = true;

    public SlideShow() {
        setOpaque(false);
        layout = new MigLayout("inset 0");
        panel = new JPanel(layout);
        pagination = new Pagination();
        makeAnimation();
        delay = 1000;
        initTimer();
//        pagination.setEventPagination((int pageClick) -> {
//            if (!animator.isRunning()) {
//                if (pageClick != currentIndex) {
//                    timer.restart();
//                    next = currentIndex < pageClick;
//                    if (next) {
//                        componentOut = panel.getComponent(checkNext(currentIndex));
//                        currentIndex = getNext(pageClick - 1);
//                        componentShow = panel.getComponent(currentIndex);
//                        animator.start();
//                    } else {
//                        componentOut = panel.getComponent(checkBack(currentIndex));
//                        currentIndex = getBack(pageClick + 1);
//                        componentShow = panel.getComponent(currentIndex);
//                        animator.start();
//                    }
//                }
//            }
//        });

        setLayer(pagination, JLayeredPane.POPUP_LAYER);
        setLayout(new MigLayout("fill, inset 0",
                "[fill, center]", "0[fill]0"));
        add(pagination, "pos 0.5al 1al n n");
        add(panel, "w 100%-6!");
    }

    private void initTimer() {
        timer = new Timer(delay, (ActionEvent e) -> {
            if (!autoNext) {
                animator.stop();
                timer.stop();
                return;
            }
            next();
        });
    }

    public void initSlideshow(Component... coms) {
        if (coms.length >= 2) {
            for (Component com : coms) {
                com.setVisible(false);
                panel.add(com, "pos 0 0 0 0");
            }
            if (panel.getComponentCount() > 0) {
                componentShow = panel.getComponent(0);
                componentShow.setVisible(true);
                layout.setComponentConstraints(componentShow,
                        "pos 0 0 100% 100%");
            }
            pagination.setTotalPage(panel.getComponentCount());
            pagination.setCurrentIndex(0);
        }
    }

    public void setTimer(int delay) {
        this.delay = delay;
        timer.setDelay(delay);
    }

    public void next() {
        if (!animator.isRunning()) {
            timer.restart();
            next = true;
            currentIndex = getNext(currentIndex);
            componentShow = panel.getComponent(currentIndex);
            componentOut = panel.getComponent(checkNext(currentIndex - 1));
            animator.start();
        }
    }

    public void back() {
        if (!animator.isRunning()) {
            timer.restart();
            next = false;
            currentIndex = getBack(currentIndex);
            componentShow = panel.getComponent(currentIndex);
            componentOut = panel.getComponent(checkBack(currentIndex + 1));
            animator.start();
        }
    }

    private int getNext(int index) {
        if (!autoNext) {
            return index == panel.getComponentCount() - 1 ? index : index + 1;
        }
        return index == panel.getComponentCount() - 1 ? 0 : index + 1;
    }

    private int checkNext(int index) {
        return index == -1 ? panel.getComponentCount() - 1 : index;
    }

    private int getBack(int index) {
        return index == 0 ? panel.getComponentCount() - 1 : index - 1;
    }

    private int checkBack(int index) {
        return index == panel.getComponentCount() ? 0 : index;
    }

    public int getCompCount() {
        return panel.getComponentCount();
    }

    private void makeAnimation() {
        target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                componentShow.setVisible(true);
                componentOut.setVisible(true);
                pagination.setIndex(currentIndex);
            }

            @Override
            public void timingEvent(float fraction) {
                double width = panel.getWidth();
                int location = (int) (width * fraction);
                int locationShow = (int) (width * (1f - fraction));
                if (next) {
                    layout.setComponentConstraints(componentShow, "pos " + locationShow + " 0 100% 100%, w 100%!");
                    layout.setComponentConstraints(componentOut, "pos -" + location + " 0 " + (width - location) + " 100%");
                } else {
                    layout.setComponentConstraints(componentShow, "pos -" + locationShow + " 0 " + (width - locationShow) + " 100%");
                    layout.setComponentConstraints(componentOut, "pos " + location + " 0 100% 100%, w 100%!");
                }
                pagination.setAnimation(fraction);
                panel.revalidate();
                revalidate();
            }

            @Override
            public void end() {
                componentOut.setVisible(false);
                layout.setComponentConstraints(componentShow, "pos 0 0 100% 100%");
            }
        };
        animator = new Animator(1000, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void setAutoNext(boolean b) {
        this.autoNext = b;
        initTimer();
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
        animator.stop();
    }

    public boolean checkIndex() {
        return currentIndex == panel.getComponentCount() - 1;
    }
}
