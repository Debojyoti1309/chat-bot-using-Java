package b4x.chatbot.df;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class geral {
private static geral mostCurrent = new geral();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public b4x.chatbot.df.main _main = null;
public b4x.chatbot.df.starter _starter = null;
public static String  _imagem_redonda(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ImageViewWrapper _iv,String _pasta,String _imagem) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _input = null;
int _l = 0;
anywheresoftware.b4a.objects.B4XCanvas _c = null;
anywheresoftware.b4a.objects.B4XViewWrapper _xview = null;
anywheresoftware.b4a.objects.B4XCanvas.B4XPath _path = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _res = null;
 //BA.debugLineNum = 28;BA.debugLine="Sub Imagem_Redonda (iv As ImageView, pasta As Stri";
 //BA.debugLineNum = 29;BA.debugLine="Private Input As B4XBitmap = LoadBitmapResize(pas";
_input = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
_input.setObject((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(_pasta,_imagem,_iv.getWidth(),_iv.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 30;BA.debugLine="If Input.Width <> Input.Height Then";
if (_input.getWidth()!=_input.getHeight()) { 
 //BA.debugLineNum = 31;BA.debugLine="Dim l As Int = Min(Input.Width, Input.Height)";
_l = (int) (anywheresoftware.b4a.keywords.Common.Min(_input.getWidth(),_input.getHeight()));
 //BA.debugLineNum = 32;BA.debugLine="Input = Input.Crop(Input.Width / 2 - l / 2, Inpu";
_input = _input.Crop((int) (_input.getWidth()/(double)2-_l/(double)2),(int) (_input.getHeight()/(double)2-_l/(double)2),_l,_l);
 };
 //BA.debugLineNum = 34;BA.debugLine="Dim c As B4XCanvas";
_c = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 35;BA.debugLine="Dim xview As B4XView = xui.CreatePanel(\"\")";
_xview = new anywheresoftware.b4a.objects.B4XViewWrapper();
_xview = _xui.CreatePanel((_ba.processBA == null ? _ba : _ba.processBA),"");
 //BA.debugLineNum = 36;BA.debugLine="xview.SetLayoutAnimated(0, 0, 0, iv.Width, iv.Wid";
_xview.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_iv.getWidth(),_iv.getWidth());
 //BA.debugLineNum = 37;BA.debugLine="c.Initialize(xview)";
_c.Initialize(_xview);
 //BA.debugLineNum = 38;BA.debugLine="Dim path As B4XPath";
_path = new anywheresoftware.b4a.objects.B4XCanvas.B4XPath();
 //BA.debugLineNum = 39;BA.debugLine="path.InitializeOval(c.TargetRect)";
_path.InitializeOval(_c.getTargetRect());
 //BA.debugLineNum = 40;BA.debugLine="c.ClipPath(path)";
_c.ClipPath(_path);
 //BA.debugLineNum = 41;BA.debugLine="c.DrawBitmap(Input.Resize(iv.Width, iv.Width, Fal";
_c.DrawBitmap((android.graphics.Bitmap)(_input.Resize(_iv.getWidth(),_iv.getWidth(),anywheresoftware.b4a.keywords.Common.False).getObject()),_c.getTargetRect());
 //BA.debugLineNum = 42;BA.debugLine="c.RemoveClip";
_c.RemoveClip();
 //BA.debugLineNum = 43;BA.debugLine="c.Invalidate";
_c.Invalidate();
 //BA.debugLineNum = 44;BA.debugLine="Dim res As B4XBitmap = c.CreateBitmap";
_res = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
_res = _c.CreateBitmap();
 //BA.debugLineNum = 45;BA.debugLine="c.Release";
_c.Release();
 //BA.debugLineNum = 46;BA.debugLine="iv.Bitmap = res";
_iv.setBitmap((android.graphics.Bitmap)(_res.getObject()));
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 3;BA.debugLine="End Sub";
return "";
}
public static String  _set_statusbarcolor(anywheresoftware.b4a.BA _ba,int _clr) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4j.object.JavaObject _window = null;
 //BA.debugLineNum = 15;BA.debugLine="Sub Set_StatusBarColor(clr As Int)";
 //BA.debugLineNum = 16;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 17;BA.debugLine="If p.SdkVersion >= 21 Then";
if (_p.getSdkVersion()>=21) { 
 //BA.debugLineNum = 18;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 19;BA.debugLine="jo.InitializeContext";
_jo.InitializeContext((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 20;BA.debugLine="Dim window As JavaObject = jo.RunMethodJO(\"getWi";
_window = new anywheresoftware.b4j.object.JavaObject();
_window = _jo.RunMethodJO("getWindow",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 21;BA.debugLine="window.RunMethod(\"addFlags\", Array (0x80000000))";
_window.RunMethod("addFlags",new Object[]{(Object)(0x80000000)});
 //BA.debugLineNum = 22;BA.debugLine="window.RunMethod(\"clearFlags\", Array (0x04000000";
_window.RunMethod("clearFlags",new Object[]{(Object)(0x04000000)});
 //BA.debugLineNum = 23;BA.debugLine="window.RunMethod(\"setStatusBarColor\", Array(clr)";
_window.RunMethod("setStatusBarColor",new Object[]{(Object)(_clr)});
 };
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _setpadding(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _v,int _left,int _top,int _right,int _bottom) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 62;BA.debugLine="Sub setPadding(v As View, Left As Int, Top As Int,";
 //BA.debugLineNum = 63;BA.debugLine="Dim jo = v As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_v.getObject()));
 //BA.debugLineNum = 64;BA.debugLine="jo.RunMethod(\"setPadding\", Array As Object(Left,";
_jo.RunMethod("setPadding",new Object[]{(Object)(_left),(Object)(_top),(Object)(_right),(Object)(_bottom)});
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static int  _tamanho_textohorizontal(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _lb,String _texto) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
 //BA.debugLineNum = 52;BA.debugLine="Sub Tamanho_TextoHorizontal(lb As Label, Texto As";
 //BA.debugLineNum = 53;BA.debugLine="Private bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 54;BA.debugLine="bmp.InitializeMutable(1dip, 1dip)";
_bmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 55;BA.debugLine="Private cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 56;BA.debugLine="cvs.Initialize2(bmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 57;BA.debugLine="Return cvs.MeasureStringWidth(Texto, lb.Typeface";
if (true) return (int) (_cvs.MeasureStringWidth(_texto,_lb.getTypeface(),_lb.getTextSize()));
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return 0;
}
public static int  _tamanho_textovertical(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _lb,String _texto) throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
 //BA.debugLineNum = 7;BA.debugLine="Sub Tamanho_TextoVertical(lb As Label,texto As Str";
 //BA.debugLineNum = 8;BA.debugLine="If texto.Length < 1 Then Return 0";
if (_texto.length()<1) { 
if (true) return (int) (0);};
 //BA.debugLineNum = 9;BA.debugLine="Private su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 10;BA.debugLine="Return su.MeasureMultilineTextHeight(lb,texto)";
if (true) return _su.MeasureMultilineTextHeight((android.widget.TextView)(_lb.getObject()),BA.ObjectToCharSequence(_texto));
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return 0;
}
}
