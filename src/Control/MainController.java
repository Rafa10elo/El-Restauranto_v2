package Control;

import Model.User;
import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Map;


public class MainController {
   static User user;
    LoginAndRegistrationFrame loginAndRegistrationFrame;
    LoginAndRegisterManager loginAndRegisterManager;
    ActionListener logoutListener;
    ActionListener loginListener;
    MainFrame mainFrame ;
    ProfilePanel profilePanel;
    MealsPanel mealsPanel;
    ReportPanel reportPanel;
    AllOrdersPanel allOrdersPanel;
    ProfileController profileController;
    Orders orders = Orders.getOrdersSing();
    Users users= new Users();
    Meals meals = new Meals();
    Payments payments = new Payments() ;
    MealsController mealsController;
    Report report = new Report(0,0) ;


    public MainController(){
        users.readerThread();
        meals.readerThread();
        payments.readerThread();
//        Payment.loadCounterFromFile();
        report =report.loadFromFile();



        loginAndRegistrationFrame = new LoginAndRegistrationFrame();

        loginAndRegisterManager = new LoginAndRegisterManager(users,loginAndRegistrationFrame,user);

        WindowAdapter windowAdapter = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                users.writerThread();
                report.writerThread();
                payments.writerThread();
                meals.writerThread();
                System.exit(0);
            }
        };
loginAndRegistrationFrame.addWindowListener(windowAdapter);
        loginListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user =  loginAndRegisterManager.loginCheck();
                if(user!=null) {

                    loginAndRegistrationFrame.dispose();
                    profilePanel= new ProfilePanel(user);
                    profileController = new ProfileController(users,profilePanel,report);
                    reportPanel = new ReportPanel(report,users.getUsers().size(),meals.getMeals().size());
                    mealsPanel = new MealsPanel(user);
                    allOrdersPanel= new AllOrdersPanel(user);
                    mainFrame = createMainFrame(user,profilePanel,reportPanel,allOrdersPanel, report, users, meals);
                    mainFrame.addWindowListener(windowAdapter);
                    profilePanel.logoutButton.addActionListener(logoutListener);

                    mealsController = new MealsController(meals, mainFrame.mealsPanel, user) ;

                    if( user.getUserType() == 0){

                        // ADD ORDER BUTTON (PAY + CANCEL)
                        ActionListener addOrderListener = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (mainFrame.mealsPanel.getSidePanel().paymentInfoValid()){
                                    // payment
                                    Payment payment ;
                                    if (mainFrame.mealsPanel.getSidePanel().getMethod().equals("cash")){
                                        // if the payment method is cash so create a payment
                                        payment = new Payment(mainFrame.mealsPanel.getSidePanel().getPaymentAmount(), "cash") ;
                                    }else{
                                        if( !payments.checkTheCreditCard(mainFrame.mealsPanel.getSidePanel().getCreditCardId().getText()) ){
                                            // if the payment method is credit card and the credit card id is wrong so theres no payment
                                            payment = null ;
                                        }else{
                                            // if the payment method is credit cad and the credit card id is valid so create a payment
                                            payment = new Payment(mainFrame.mealsPanel.getSidePanel().getPaymentAmount(), "credit card") ;
                                        }
                                    }

                                    Order order ;
                                    if ( payment != null ){
                                        // if there's a payment
                                        // add to payments, write
//                                        Payment.saveCounterToFile();
                                        payments.addPayment(payment);
                                        payments.writerThread();

                                        // create order, add to orders, write
                                        order = new Order(mainFrame.mealsPanel.getSidePanel().getOrderMeals(), mainFrame.mealsPanel.getSidePanel().getTotalPrice(),
                                                mainFrame.mealsPanel.getSidePanel().getTips(), Order.Status.PREPARING,LocalDateTime.now().plusSeconds(12),
                                                payment.getPaymentId(), user.getUserName(), mainFrame.mealsPanel.getSidePanel().getInRestaurant());
                                        orders.addOrderForUser(user, order);
                                        users.writerThread();
                                        mainFrame.allOrdersPanel.addNewOrder(order,user,orders);
                                        // edit the report : total money, number of orders, ordering users, ordered meals
                                        report.addToTotalMoney(payment.getAmount());
                                        report.increaseNumberOfOrders();
                                        report.incrementUserCount(user, 1);
                                        for (Map.Entry<Meal, Integer> mealCnt : order.getMeals().entrySet()){
                                            report.incrementMealCount(mealCnt.getKey(), mealCnt.getValue());
                                        }
                                       report.writerThread();

                                        // close dialog
                                        mainFrame.mealsPanel.getSidePanel().orderReset();
                                        JOptionPane.showMessageDialog(mainFrame, "Your order is successfully added! :) ", "order added", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    else{
                                        // if there's no payment
                                        // create a canceled order, add to orders, write
                                        order = new Order(mainFrame.mealsPanel.getSidePanel().getOrderMeals(), mainFrame.mealsPanel.getSidePanel().getTotalPrice()
                                                , mainFrame.mealsPanel.getSidePanel().getTips(), Order.Status.CANCELED, user.getUserName(),
                                                mainFrame.mealsPanel.getSidePanel().getInRestaurant());
                                        orders.addOrderForUser(user, order);
                                        mainFrame.allOrdersPanel.addNewOrder(order,user,orders);
                                        users.writerThread();
                                        // edit the report : number of orders X( total money, ordering users, ordered meals)
                                        report.increaseNumberOfOrders();
                                        report.writerThread();

                                        // close dialog
                                        mainFrame.mealsPanel.getSidePanel().orderReset();
                                        JOptionPane.showMessageDialog(mainFrame, "the credit card ID is not valid, your order will be canceled", "incorrect ID", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }

                        };
                        mainFrame.mealsPanel.getSidePanel().getPayButton().addActionListener(addOrderListener);

                        ActionListener resetOrderListener = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
//                                // create a canceled order, add to orders, write
                                Order order = new Order(mainFrame.mealsPanel.getSidePanel().getOrderMeals(), mainFrame.mealsPanel.getSidePanel().getTotalPrice()
                                        , mainFrame.mealsPanel.getSidePanel().getTips(), Order.Status.CANCELED, user.getUserName(),
                                        mainFrame.mealsPanel.getSidePanel().getInRestaurant());
                                orders.addOrderForUser(user, order);
                                users.writerThread();
                                // edit report --------------------------------------------------------
                                mainFrame.mealsPanel.getSidePanel().orderReset();
                                JOptionPane.showMessageDialog(mainFrame, "Your order is reset", "", JOptionPane.INFORMATION_MESSAGE);
                            }
                        };
                        mainFrame.mealsPanel.getSidePanel().getResetOrderButton().addActionListener(resetOrderListener);

                    }
                }
            }
        };

        logoutListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = null;
                mainFrame.dispose();
                loginAndRegistrationFrame = new LoginAndRegistrationFrame();
                loginAndRegisterManager=new LoginAndRegisterManager(users,loginAndRegistrationFrame,user);
                loginAndRegistrationFrame.addWindowListener(windowAdapter);
                loginAndRegistrationFrame.loginPanel.loginButton.addActionListener(loginListener);
            }
        };

        loginAndRegistrationFrame.loginPanel.loginButton.addActionListener(loginListener);

    }

    MainFrame createMainFrame(User user, ProfilePanel profilePanel,ReportPanel reportPanel,AllOrdersPanel allOrdersPanel,Report report,Users users,Meals meals){
        MainFrame mainFrame=new MainFrame(user,profilePanel,reportPanel,allOrdersPanel);
        mainFrame.profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.cardLayout.show(mainFrame.cardsPanel, "profilePanel");
            }
        });

        mainFrame.mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.cardLayout.show(MainFrame.cardsPanel, "mealsPanel");
            }
        });

        mainFrame.allOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.cardLayout.show(mainFrame.cardsPanel, "allOrdersPanel");
            }
        });
        mainFrame.reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPanel updatedReport = new ReportPanel(report, users.getUsers().size(), meals.getMeals().size());
                mainFrame.cardsPanel.add(updatedReport, "reportPanel");
                mainFrame.cardLayout.show(mainFrame.cardsPanel, "reportPanel");
            }
        });

        mainFrame.revalidate();
        mainFrame.repaint();
        return mainFrame;
    }
    public static User getUser(){
        return user;
    }


}



