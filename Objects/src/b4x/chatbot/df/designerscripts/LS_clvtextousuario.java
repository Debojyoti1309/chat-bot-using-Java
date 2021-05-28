package b4x.chatbot.df.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_clvtextousuario{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pfundofalausuario").vw.setLeft((int)((10d / 100 * width)));
views.get("pfundofalausuario").vw.setWidth((int)((96d / 100 * width) - ((10d / 100 * width))));
views.get("pfundofalausuario").vw.setTop((int)((0d / 100 * height)));
views.get("pfundofalausuario").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("lbtextousuario").vw.setLeft((int)((2d / 100 * width)));
views.get("lbtextousuario").vw.setWidth((int)((84d / 100 * width) - ((2d / 100 * width))));
views.get("lbtextousuario").vw.setTop((int)((0d / 100 * height)));
views.get("lbtextousuario").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("imgpontausuario").vw.setLeft((int)((92d / 100 * width)));
views.get("imgpontausuario").vw.setWidth((int)((98d / 100 * width) - ((92d / 100 * width))));
views.get("imgpontausuario").vw.setTop((int)((0d / 100 * height)));
views.get("imgpontausuario").vw.setHeight((int)((30d / 100 * height) - ((0d / 100 * height))));

}
}