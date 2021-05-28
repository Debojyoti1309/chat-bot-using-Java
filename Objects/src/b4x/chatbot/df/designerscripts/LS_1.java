package b4x.chatbot.df.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 1;BA.debugLine="AutoScaleAll"[1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 2;BA.debugLine="pTopMenu.SetLeftAndRight(0%x,100%x)"[1/General script]
views.get("ptopmenu").vw.setLeft((int)((0d / 100 * width)));
views.get("ptopmenu").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 3;BA.debugLine="pTopMenu.SetTopAndBottom(0%y,9%y)"[1/General script]
views.get("ptopmenu").vw.setTop((int)((0d / 100 * height)));
views.get("ptopmenu").vw.setHeight((int)((9d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="icMenuTopMenu.SetLeftAndRight(0%x,15%x)"[1/General script]
views.get("icmenutopmenu").vw.setLeft((int)((0d / 100 * width)));
views.get("icmenutopmenu").vw.setWidth((int)((15d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 6;BA.debugLine="icMenuTopMenu.SetTopAndBottom(0%y,9%y)"[1/General script]
views.get("icmenutopmenu").vw.setTop((int)((0d / 100 * height)));
views.get("icmenutopmenu").vw.setHeight((int)((9d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 8;BA.debugLine="lbTituloTopMenu.SetLeftAndRight(15%x,85%x)"[1/General script]
views.get("lbtitulotopmenu").vw.setLeft((int)((15d / 100 * width)));
views.get("lbtitulotopmenu").vw.setWidth((int)((85d / 100 * width) - ((15d / 100 * width))));
//BA.debugLineNum = 9;BA.debugLine="lbTituloTopMenu.SetTopAndBottom(0%y,9%y)"[1/General script]
views.get("lbtitulotopmenu").vw.setTop((int)((0d / 100 * height)));
views.get("lbtitulotopmenu").vw.setHeight((int)((9d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 11;BA.debugLine="icConfigTopMenu.SetLeftAndRight(85%x,100%x)"[1/General script]
views.get("icconfigtopmenu").vw.setLeft((int)((85d / 100 * width)));
views.get("icconfigtopmenu").vw.setWidth((int)((100d / 100 * width) - ((85d / 100 * width))));
//BA.debugLineNum = 12;BA.debugLine="icConfigTopMenu.SetTopAndBottom(0%y,9%y)"[1/General script]
views.get("icconfigtopmenu").vw.setTop((int)((0d / 100 * height)));
views.get("icconfigtopmenu").vw.setHeight((int)((9d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 14;BA.debugLine="clvMensagens.SetLeftAndRight(0%x,100%x)"[1/General script]
views.get("clvmensagens").vw.setLeft((int)((0d / 100 * width)));
views.get("clvmensagens").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 15;BA.debugLine="clvMensagens.SetTopAndBottom(pTopMenu.Bottom,91%y)"[1/General script]
views.get("clvmensagens").vw.setTop((int)((views.get("ptopmenu").vw.getTop() + views.get("ptopmenu").vw.getHeight())));
views.get("clvmensagens").vw.setHeight((int)((91d / 100 * height) - ((views.get("ptopmenu").vw.getTop() + views.get("ptopmenu").vw.getHeight()))));
//BA.debugLineNum = 17;BA.debugLine="pEscrever.SetLeftAndRight(2%x,80%x)"[1/General script]
views.get("pescrever").vw.setLeft((int)((2d / 100 * width)));
views.get("pescrever").vw.setWidth((int)((80d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 18;BA.debugLine="pEscrever.SetTopAndBottom(92%y,99%y)"[1/General script]
views.get("pescrever").vw.setTop((int)((92d / 100 * height)));
views.get("pescrever").vw.setHeight((int)((99d / 100 * height) - ((92d / 100 * height))));
//BA.debugLineNum = 20;BA.debugLine="edEscrever.SetLeftAndRight(2%x,76%x)"[1/General script]
views.get("edescrever").vw.setLeft((int)((2d / 100 * width)));
views.get("edescrever").vw.setWidth((int)((76d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 21;BA.debugLine="edEscrever.SetTopAndBottom(0%y,7%y)"[1/General script]
views.get("edescrever").vw.setTop((int)((0d / 100 * height)));
views.get("edescrever").vw.setHeight((int)((7d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 23;BA.debugLine="icEscrever.SetLeftAndRight(84%x,98%x)"[1/General script]
views.get("icescrever").vw.setLeft((int)((84d / 100 * width)));
views.get("icescrever").vw.setWidth((int)((98d / 100 * width) - ((84d / 100 * width))));
//BA.debugLineNum = 24;BA.debugLine="icEscrever.SetTopAndBottom(92%y,99%y)"[1/General script]
views.get("icescrever").vw.setTop((int)((92d / 100 * height)));
views.get("icescrever").vw.setHeight((int)((99d / 100 * height) - ((92d / 100 * height))));

}
}