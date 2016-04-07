package com.TMAProject.EzzSteel.Activities.Base.Parameters;

/**
 * Created by TarekkMA on 4/2/16.
 */
public class Template {
    public BackButtonInfo backButtonInfo;
    public NavInfo navInfo;
    public TitleInfo titleInfo;


    public static final int TEMPLATE_HOME = 1;
    public static final int TEMPLATE_PAGE = 2;
    public static final int TEMPLATE_NOTIFCATION = 3;


    public Template(int templateType) {
        switch (templateType){
            case TEMPLATE_HOME:

                break;
            case TEMPLATE_PAGE:

                break;
            default:
                try {
                    throw new Exception("Enter valid template");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
