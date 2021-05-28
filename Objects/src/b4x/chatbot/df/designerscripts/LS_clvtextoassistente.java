package b4x.chatbot.df.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_clvtextoassistente{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pfundofala").vw.setLeft((int)((4d / 100 * width)));
views.get("pfundofala").vw.setWidth((int)((90d / 100 * width) - ((4d / 100 * width))));
views.get("pfundofala").vw.setTop((int)((0d / 100 * height)));
views.get("pfundofala").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("lbtextoassistente").vw.setLeft((int)((2d / 100 * width)));
views.get("lbtextoassistente").vw.setWidth((int)((84d / 100 * width) - ((2d / 100 * width))));
views.get("lbtextoassistente").vw.setTop((int)((0d / 100 * height)));
views.get("lbtextoassistente").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("imgpontaassistente").vw.setLeft((int)((2d / 100 * width)));
views.get("imgpontaassistente").vw.setWidth((int)((8d / 100 * width) - ((2d / 100 * width))));
views.get("imgpontaassistente").vw.setTop((int)((0d / 100 * height)));
views.get("imgpontaassistente").vw.setHeight((int)((30d / 100 * height) - ((0d / 100 * height))));

}
}