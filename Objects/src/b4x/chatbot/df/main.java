package b4x.chatbot.df;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4x.chatbot.df", "b4x.chatbot.df.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4x.chatbot.df", "b4x.chatbot.df.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4x.chatbot.df.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.StringUtils _su = null;
public anywheresoftware.b4a.objects.IME _ime = null;
public static int _heightteclado = 0;
public static int _tamanhomaximo = 0;
public b4a.example3.customlistview _clvmensagens = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edescrever = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _icescrever = null;
public anywheresoftware.b4a.objects.PanelWrapper _pescrever = null;
public anywheresoftware.b4a.objects.PanelWrapper _ptopmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbtitulotopmenu = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _icmenutopmenu = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _icconfigtopmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbtextoassistente = null;
public anywheresoftware.b4a.objects.PanelWrapper _pfundofala = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpontaassistente = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbtextousuario = null;
public anywheresoftware.b4a.objects.PanelWrapper _pfundofalausuario = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgpontausuario = null;
public b4x.chatbot.df.starter _starter = null;
public b4x.chatbot.df.geral _geral = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static class _mensagemtexto{
public boolean IsInitialized;
public String mensagem;
public boolean assistente;
public void Initialize() {
IsInitialized = true;
mensagem = "";
assistente = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitulo = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cc = null;
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 54;BA.debugLine="geral.Set_StatusBarColor(Colors.RGB(89,89,89))";
mostCurrent._geral._set_statusbarcolor /*String*/ (mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (89),(int) (89),(int) (89)));
 //BA.debugLineNum = 55;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 57;BA.debugLine="ime.Initialize(\"ime\")";
mostCurrent._ime.Initialize("ime");
 //BA.debugLineNum = 58;BA.debugLine="ime.AddHeightChangedEvent";
mostCurrent._ime.AddHeightChangedEvent(mostCurrent.activityBA);
 //BA.debugLineNum = 62;BA.debugLine="Private csTitulo As CSBuilder";
_cstitulo = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 63;BA.debugLine="csTitulo.Initialize";
_cstitulo.Initialize();
 //BA.debugLineNum = 64;BA.debugLine="csTitulo.Color(Colors.White).Append(\"B4X CHAT\").C";
_cstitulo.Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("B4X CHAT")).Color(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (113),(int) (62))).Append(BA.ObjectToCharSequence("BOT")).PopAll();
 //BA.debugLineNum = 65;BA.debugLine="lbTituloTopMenu.Text = csTitulo";
mostCurrent._lbtitulotopmenu.setText(BA.ObjectToCharSequence(_cstitulo.getObject()));
 //BA.debugLineNum = 66;BA.debugLine="icMenuTopMenu.SetBackgroundImage(LoadBitmapResize";
mostCurrent._icmenutopmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png",mostCurrent._icmenutopmenu.getWidth(),mostCurrent._icmenutopmenu.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 67;BA.debugLine="icConfigTopMenu.SetBackgroundImage(LoadBitmapResi";
mostCurrent._icconfigtopmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"settings.png",mostCurrent._icconfigtopmenu.getWidth(),mostCurrent._icconfigtopmenu.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 71;BA.debugLine="Private cc As ColorDrawable";
_cc = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 72;BA.debugLine="cc.Initialize2(Colors.RGB(250,250,250),10,2,Color";
_cc.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)),(int) (10),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 73;BA.debugLine="pEscrever.Background = cc";
mostCurrent._pescrever.setBackground((android.graphics.drawable.Drawable)(_cc.getObject()));
 //BA.debugLineNum = 74;BA.debugLine="edEscrever.Background = Null";
mostCurrent._edescrever.setBackground((android.graphics.drawable.Drawable)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 75;BA.debugLine="geral.setPadding(edEscrever,0,0,0,0) 'REMOVE PADD";
mostCurrent._geral._setpadding /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(mostCurrent._edescrever.getObject())),(int) (0),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 77;BA.debugLine="icEscrever.SetBackgroundImage(LoadBitmapResize(Fi";
mostCurrent._icescrever.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"eVoz.png",mostCurrent._icescrever.getWidth(),mostCurrent._icescrever.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 78;BA.debugLine="icEscrever.Tag = \"voz\"";
mostCurrent._icescrever.setTag((Object)("voz"));
 //BA.debugLineNum = 82;BA.debugLine="IME_HeightChanged(100%y,0)";
_ime_heightchanged(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),(int) (0));
 //BA.debugLineNum = 83;BA.debugLine="tamanhoMaximo = su.MeasureMultilineTextHeight(edE";
_tamanhomaximo = (int) (_su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._edescrever.getObject()),BA.ObjectToCharSequence("teste de tamanho!"))*6);
 //BA.debugLineNum = 86;BA.debugLine="Escreve_Bot(\"Hey! How are you?\")";
_escreve_bot("Hey! How are you?");
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 92;BA.debugLine="AjustaTamanho_Clv";
_ajustatamanho_clv();
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static void  _ajustatamanho_clv() throws Exception{
ResumableSub_AjustaTamanho_Clv rsub = new ResumableSub_AjustaTamanho_Clv(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_AjustaTamanho_Clv extends BA.ResumableSub {
public ResumableSub_AjustaTamanho_Clv(b4x.chatbot.df.main parent) {
this.parent = parent;
}
b4x.chatbot.df.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 226;BA.debugLine="clvMensagens.AsView.Top = 0 + pTopMenu.Height";
parent.mostCurrent._clvmensagens._asview().setTop((int) (0+parent.mostCurrent._ptopmenu.getHeight()));
 //BA.debugLineNum = 227;BA.debugLine="clvMensagens.AsView.Height = pEscrever.Top - pTop";
parent.mostCurrent._clvmensagens._asview().setHeight((int) (parent.mostCurrent._pescrever.getTop()-parent.mostCurrent._ptopmenu.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)));
 //BA.debugLineNum = 228;BA.debugLine="clvMensagens.Base_Resize(clvMensagens.AsView.Widt";
parent.mostCurrent._clvmensagens._base_resize(parent.mostCurrent._clvmensagens._asview().getWidth(),parent.mostCurrent._clvmensagens._asview().getHeight());
 //BA.debugLineNum = 229;BA.debugLine="Sleep(0) 'PARA TER CERTEZA QUE AJUSTOU O TAMANHO,";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 7;
return;
case 7:
//C
this.state = 1;
;
 //BA.debugLineNum = 230;BA.debugLine="If clvMensagens.Size > 0 Then clvMensagens.JumpTo";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.mostCurrent._clvmensagens._getsize()>0) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
parent.mostCurrent._clvmensagens._jumptoitem((int) (parent.mostCurrent._clvmensagens._getsize()-1));
if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _clvmensagens_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub clvMensagens_ItemClick (Index As Int, Value As";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _clvmensagens_visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _extrasize = 0;
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4x.chatbot.df.main._mensagemtexto _m = null;
int _margemtop = 0;
int _margembottom = 0;
 //BA.debugLineNum = 104;BA.debugLine="Sub clvMensagens_VisibleRangeChanged (FirstIndex A";
 //BA.debugLineNum = 105;BA.debugLine="Dim ExtraSize As Int = 2";
_extrasize = (int) (2);
 //BA.debugLineNum = 106;BA.debugLine="For i = 0 To clvMensagens.Size - 1";
{
final int step2 = 1;
final int limit2 = (int) (mostCurrent._clvmensagens._getsize()-1);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 107;BA.debugLine="Dim p As Panel = clvMensagens.GetPanel(i)";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p.setObject((android.view.ViewGroup)(mostCurrent._clvmensagens._getpanel(_i).getObject()));
 //BA.debugLineNum = 108;BA.debugLine="If i > FirstIndex - ExtraSize And i < LastIndex";
if (_i>_firstindex-_extrasize && _i<_lastindex+_extrasize) { 
 //BA.debugLineNum = 109;BA.debugLine="If p.NumberOfViews = 0 Then";
if (_p.getNumberOfViews()==0) { 
 //BA.debugLineNum = 111;BA.debugLine="Dim m As mensagemTexto = clvMensagens.GetValue";
_m = (b4x.chatbot.df.main._mensagemtexto)(mostCurrent._clvmensagens._getvalue(_i));
 //BA.debugLineNum = 113;BA.debugLine="If m.assistente Then";
if (_m.assistente /*boolean*/ ) { 
 //BA.debugLineNum = 115;BA.debugLine="p.LoadLayout(\"clvTextoAssistente\")";
_p.LoadLayout("clvTextoAssistente",mostCurrent.activityBA);
 //BA.debugLineNum = 116;BA.debugLine="lbTextoAssistente.Text = m.mensagem";
mostCurrent._lbtextoassistente.setText(BA.ObjectToCharSequence(_m.mensagem /*String*/ ));
 //BA.debugLineNum = 118;BA.debugLine="imgPontaAssistente.Height = 3%y";
mostCurrent._imgpontaassistente.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA));
 //BA.debugLineNum = 119;BA.debugLine="imgPontaAssistente.Top = 0";
mostCurrent._imgpontaassistente.setTop((int) (0));
 //BA.debugLineNum = 120;BA.debugLine="imgPontaAssistente.SetBackgroundImage(LoadBit";
mostCurrent._imgpontaassistente.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ponta.png",mostCurrent._imgpontaassistente.getWidth(),mostCurrent._imgpontaassistente.getHeight(),anywheresoftware.b4a.keywords.Common.False).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 123;BA.debugLine="Private margemTop As Int = 1%y : Private marg";
_margemtop = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA);
 //BA.debugLineNum = 123;BA.debugLine="Private margemTop As Int = 1%y : Private marg";
_margembottom = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA);
 //BA.debugLineNum = 124;BA.debugLine="lbTextoAssistente.Height = geral.Tamanho_Text";
mostCurrent._lbtextoassistente.setHeight(mostCurrent._geral._tamanho_textovertical /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextoassistente,mostCurrent._lbtextoassistente.getText()));
 //BA.debugLineNum = 125;BA.debugLine="lbTextoAssistente.Top = 0%y + margemTop";
mostCurrent._lbtextoassistente.setTop((int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA)+_margemtop));
 //BA.debugLineNum = 128;BA.debugLine="If geral.Tamanho_TextoHorizontal(lbTextoAssis";
if (mostCurrent._geral._tamanho_textohorizontal /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextoassistente,mostCurrent._lbtextoassistente.getText())<anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (82),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 129;BA.debugLine="lbTextoAssistente.Width = geral.Tamanho_Text";
mostCurrent._lbtextoassistente.setWidth(mostCurrent._geral._tamanho_textohorizontal /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextoassistente,mostCurrent._lbtextoassistente.getText()));
 //BA.debugLineNum = 130;BA.debugLine="lbTextoAssistente.SingleLine = True";
mostCurrent._lbtextoassistente.setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="pFundoFala.Width = lbTextoAssistente.Width +";
mostCurrent._pfundofala.setWidth((int) (mostCurrent._lbtextoassistente.getWidth()+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (4),mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 134;BA.debugLine="pFundoFala.Height = lbTextoAssistente.Height";
mostCurrent._pfundofala.setHeight((int) (mostCurrent._lbtextoassistente.getHeight()+_margemtop+_margembottom));
 //BA.debugLineNum = 135;BA.debugLine="clvMensagens.ResizeItem(i,pFundoFala.Height)";
mostCurrent._clvmensagens._resizeitem(_i,mostCurrent._pfundofala.getHeight());
 }else {
 //BA.debugLineNum = 139;BA.debugLine="p.LoadLayout(\"clvTextoUsuario\")";
_p.LoadLayout("clvTextoUsuario",mostCurrent.activityBA);
 //BA.debugLineNum = 140;BA.debugLine="lbTextoUsuario.Text = m.mensagem";
mostCurrent._lbtextousuario.setText(BA.ObjectToCharSequence(_m.mensagem /*String*/ ));
 //BA.debugLineNum = 142;BA.debugLine="imgPontaUsuario.Height = 3%y";
mostCurrent._imgpontausuario.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),mostCurrent.activityBA));
 //BA.debugLineNum = 143;BA.debugLine="imgPontaUsuario.Top = 0";
mostCurrent._imgpontausuario.setTop((int) (0));
 //BA.debugLineNum = 144;BA.debugLine="imgPontaUsuario.SetBackgroundImage(LoadBitmap";
mostCurrent._imgpontausuario.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pontaCinza.png",mostCurrent._imgpontausuario.getWidth(),mostCurrent._imgpontausuario.getHeight(),anywheresoftware.b4a.keywords.Common.False).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 147;BA.debugLine="Private margemTop As Int = 1%y : Private marg";
_margemtop = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA);
 //BA.debugLineNum = 147;BA.debugLine="Private margemTop As Int = 1%y : Private marg";
_margembottom = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA);
 //BA.debugLineNum = 148;BA.debugLine="lbTextoUsuario.Height = geral.Tamanho_TextoVe";
mostCurrent._lbtextousuario.setHeight(mostCurrent._geral._tamanho_textovertical /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextousuario,_m.mensagem /*String*/ ));
 //BA.debugLineNum = 149;BA.debugLine="lbTextoUsuario.Top = 0%y + margemTop";
mostCurrent._lbtextousuario.setTop((int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA)+_margemtop));
 //BA.debugLineNum = 152;BA.debugLine="If geral.Tamanho_TextoHorizontal(lbTextoUsuar";
if (mostCurrent._geral._tamanho_textohorizontal /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextousuario,mostCurrent._lbtextousuario.getText())<anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (82),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 153;BA.debugLine="lbTextoUsuario.Width = geral.Tamanho_TextoHo";
mostCurrent._lbtextousuario.setWidth(mostCurrent._geral._tamanho_textohorizontal /*int*/ (mostCurrent.activityBA,mostCurrent._lbtextousuario,mostCurrent._lbtextousuario.getText()));
 //BA.debugLineNum = 154;BA.debugLine="lbTextoUsuario.SingleLine = True";
mostCurrent._lbtextousuario.setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="pFundoFalaUsuario.Width = lbTextoUsuario.Wid";
mostCurrent._pfundofalausuario.setWidth((int) (mostCurrent._lbtextousuario.getWidth()+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (4),mostCurrent.activityBA)));
 //BA.debugLineNum = 156;BA.debugLine="pFundoFalaUsuario.Left = 100%x - pFundoFalaU";
mostCurrent._pfundofalausuario.setLeft((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._pfundofalausuario.getWidth()-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (4),mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 159;BA.debugLine="pFundoFalaUsuario.Height = lbTextoUsuario.Hei";
mostCurrent._pfundofalausuario.setHeight((int) (mostCurrent._lbtextousuario.getHeight()+_margemtop+_margembottom));
 //BA.debugLineNum = 160;BA.debugLine="clvMensagens.ResizeItem(i,pFundoFalaUsuario.H";
mostCurrent._clvmensagens._resizeitem(_i,mostCurrent._pfundofalausuario.getHeight());
 };
 };
 }else {
 //BA.debugLineNum = 171;BA.debugLine="If p.NumberOfViews > 0 Then";
if (_p.getNumberOfViews()>0) { 
 //BA.debugLineNum = 172;BA.debugLine="p.RemoveAllViews";
_p.RemoveAllViews();
 };
 };
 }
};
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static void  _conversa_bot() throws Exception{
ResumableSub_Conversa_Bot rsub = new ResumableSub_Conversa_Bot(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Conversa_Bot extends BA.ResumableSub {
public ResumableSub_Conversa_Bot(b4x.chatbot.df.main parent) {
this.parent = parent;
}
b4x.chatbot.df.main parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 297;BA.debugLine="If clvMensagens.Size = 2 Then";
if (true) break;

case 1:
//if
this.state = 12;
if (parent.mostCurrent._clvmensagens._getsize()==2) { 
this.state = 3;
}else if(parent.mostCurrent._clvmensagens._getsize()==4) { 
this.state = 5;
}else if(parent.mostCurrent._clvmensagens._getsize()==6) { 
this.state = 7;
}else if(parent.mostCurrent._clvmensagens._getsize()==8) { 
this.state = 9;
}else if(parent.mostCurrent._clvmensagens._getsize()==10) { 
this.state = 11;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 298;BA.debugLine="Sleep(1200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1200));
this.state = 13;
return;
case 13:
//C
this.state = 12;
;
 //BA.debugLineNum = 299;BA.debugLine="Escreve_Bot(\"What a good guy! What do you want t";
_escreve_bot("What a good guy! What do you want to do today??");
 if (true) break;

case 5:
//C
this.state = 12;
 //BA.debugLineNum = 301;BA.debugLine="Sleep(1200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1200));
this.state = 14;
return;
case 14:
//C
this.state = 12;
;
 //BA.debugLineNum = 302;BA.debugLine="Escreve_Bot(\"Sure? 🤔\")";
_escreve_bot("Sure? 🤔");
 if (true) break;

case 7:
//C
this.state = 12;
 //BA.debugLineNum = 304;BA.debugLine="Sleep(1200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1200));
this.state = 15;
return;
case 15:
//C
this.state = 12;
;
 //BA.debugLineNum = 305;BA.debugLine="Escreve_Bot(\"So let's go! 🍺🍺🍺🍻🍻\")";
_escreve_bot("So let's go! 🍺🍺🍺🍻🍻");
 if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 307;BA.debugLine="Sleep(1200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1200));
this.state = 16;
return;
case 16:
//C
this.state = 12;
;
 //BA.debugLineNum = 308;BA.debugLine="Escreve_Bot(\"what the fuck???\")";
_escreve_bot("what the fuck???");
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 310;BA.debugLine="Sleep(1200)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1200));
this.state = 17;
return;
case 17:
//C
this.state = 12;
;
 //BA.debugLineNum = 311;BA.debugLine="Escreve_Bot(\"Subscribe to: SerBermz!\")";
_escreve_bot("Subscribe to: SerBermz!");
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _edescrever_textchanged(String _old,String _new) throws Exception{
int _i = 0;
 //BA.debugLineNum = 190;BA.debugLine="Sub edEscrever_TextChanged (Old As String, New As";
 //BA.debugLineNum = 193;BA.debugLine="If New.Length > 0 Then";
if (_new.length()>0) { 
 //BA.debugLineNum = 194;BA.debugLine="icEscrever.SetBackgroundImage(LoadBitmapResize(F";
mostCurrent._icescrever.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"eMensagem.png",mostCurrent._icescrever.getWidth(),mostCurrent._icescrever.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 195;BA.debugLine="icEscrever.Tag = \"texto\"";
mostCurrent._icescrever.setTag((Object)("texto"));
 }else {
 //BA.debugLineNum = 197;BA.debugLine="icEscrever.SetBackgroundImage(LoadBitmapResize(F";
mostCurrent._icescrever.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"eVoz.png",mostCurrent._icescrever.getWidth(),mostCurrent._icescrever.getHeight(),anywheresoftware.b4a.keywords.Common.True).getObject())).setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 198;BA.debugLine="icEscrever.Tag = \"voz\"";
mostCurrent._icescrever.setTag((Object)("voz"));
 };
 //BA.debugLineNum = 202;BA.debugLine="Private i As Int = su.MeasureMultilineTextHeight(";
_i = _su.MeasureMultilineTextHeight((android.widget.TextView)(mostCurrent._edescrever.getObject()),BA.ObjectToCharSequence(_new));
 //BA.debugLineNum = 203;BA.debugLine="If i > tamanhoMaximo Then Return 'CHEGOU NO LIMIT";
if (_i>_tamanhomaximo) { 
if (true) return "";};
 //BA.debugLineNum = 205;BA.debugLine="If i > 7%y Then 'ESTÁ PEQUENO, VAMOS AUMENTAR ATÉ";
if (_i>anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 206;BA.debugLine="pEscrever.Height = i";
mostCurrent._pescrever.setHeight(_i);
 //BA.debugLineNum = 207;BA.debugLine="edEscrever.Height = i";
mostCurrent._edescrever.setHeight(_i);
 //BA.debugLineNum = 208;BA.debugLine="pEscrever.Top = heightTeclado - pEscrever.Height";
mostCurrent._pescrever.setTop((int) (_heightteclado-mostCurrent._pescrever.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)));
 //BA.debugLineNum = 209;BA.debugLine="AjustaTamanho_Clv";
_ajustatamanho_clv();
 };
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _esconde_teclado() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Sub Esconde_Teclado";
 //BA.debugLineNum = 284;BA.debugLine="edEscrever.Text = \"\"";
mostCurrent._edescrever.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 285;BA.debugLine="pEscrever.Height = 7%y";
mostCurrent._pescrever.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA));
 //BA.debugLineNum = 286;BA.debugLine="edEscrever.Height = 7%y";
mostCurrent._edescrever.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (7),mostCurrent.activityBA));
 //BA.debugLineNum = 287;BA.debugLine="ime.HideKeyboard";
mostCurrent._ime.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _escreve_bot(String _mensagem) throws Exception{
b4x.chatbot.df.main._mensagemtexto _m = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 271;BA.debugLine="Sub Escreve_Bot(mensagem As String) 'LADO ESQUERDO";
 //BA.debugLineNum = 272;BA.debugLine="Dim m As mensagemTexto";
_m = new b4x.chatbot.df.main._mensagemtexto();
 //BA.debugLineNum = 273;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 274;BA.debugLine="m.mensagem = mensagem";
_m.mensagem /*String*/  = _mensagem;
 //BA.debugLineNum = 275;BA.debugLine="m.assistente = True";
_m.assistente /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 276;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 277;BA.debugLine="p.Initialize(\"p\")";
_p.Initialize(mostCurrent.activityBA,"p");
 //BA.debugLineNum = 278;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, clvMensagens.AsView.";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._clvmensagens._asview().getWidth(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 279;BA.debugLine="clvMensagens.Add(p, m)";
mostCurrent._clvmensagens._add((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_p.getObject())),(Object)(_m));
 //BA.debugLineNum = 280;BA.debugLine="AjustaTamanho_Clv";
_ajustatamanho_clv();
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _escreve_usuario(String _mensagem) throws Exception{
b4x.chatbot.df.main._mensagemtexto _m = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 258;BA.debugLine="Sub Escreve_Usuario(mensagem As String) 'LADO DIRE";
 //BA.debugLineNum = 259;BA.debugLine="Dim m As mensagemTexto";
_m = new b4x.chatbot.df.main._mensagemtexto();
 //BA.debugLineNum = 260;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 261;BA.debugLine="m.mensagem = mensagem";
_m.mensagem /*String*/  = _mensagem;
 //BA.debugLineNum = 262;BA.debugLine="m.assistente = False";
_m.assistente /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 263;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 264;BA.debugLine="p.Initialize(\"p\")";
_p.Initialize(mostCurrent.activityBA,"p");
 //BA.debugLineNum = 265;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, clvMensagens.AsView.";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._clvmensagens._asview().getWidth(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA));
 //BA.debugLineNum = 266;BA.debugLine="clvMensagens.Add(p, m)";
mostCurrent._clvmensagens._add((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_p.getObject())),(Object)(_m));
 //BA.debugLineNum = 267;BA.debugLine="AjustaTamanho_Clv";
_ajustatamanho_clv();
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Private ime As IME";
mostCurrent._ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 23;BA.debugLine="Private heightTeclado As Int";
_heightteclado = 0;
 //BA.debugLineNum = 24;BA.debugLine="Private tamanhoMaximo As Int = 0";
_tamanhomaximo = (int) (0);
 //BA.debugLineNum = 28;BA.debugLine="Private clvMensagens As CustomListView";
mostCurrent._clvmensagens = new b4a.example3.customlistview();
 //BA.debugLineNum = 29;BA.debugLine="Private edEscrever As EditText";
mostCurrent._edescrever = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private icEscrever As ImageView";
mostCurrent._icescrever = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private pEscrever As Panel";
mostCurrent._pescrever = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private pTopMenu As Panel";
mostCurrent._ptopmenu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lbTituloTopMenu As Label";
mostCurrent._lbtitulotopmenu = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private icMenuTopMenu As ImageView";
mostCurrent._icmenutopmenu = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private icConfigTopMenu As ImageView";
mostCurrent._icconfigtopmenu = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lbTextoAssistente As Label";
mostCurrent._lbtextoassistente = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private pFundoFala As Panel";
mostCurrent._pfundofala = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private imgPontaAssistente As ImageView";
mostCurrent._imgpontaassistente = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lbTextoUsuario As Label";
mostCurrent._lbtextousuario = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private pFundoFalaUsuario As Panel";
mostCurrent._pfundofalausuario = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private imgPontaUsuario As ImageView";
mostCurrent._imgpontausuario = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _icescrever_click() throws Exception{
String _stexto = "";
 //BA.debugLineNum = 237;BA.debugLine="Sub icEscrever_Click";
 //BA.debugLineNum = 238;BA.debugLine="If icEscrever.Tag = \"texto\" Then";
if ((mostCurrent._icescrever.getTag()).equals((Object)("texto"))) { 
 //BA.debugLineNum = 239;BA.debugLine="Private sTexto As String = edEscrever.Text.Trim";
_stexto = mostCurrent._edescrever.getText().trim();
 //BA.debugLineNum = 240;BA.debugLine="Esconde_Teclado";
_esconde_teclado();
 //BA.debugLineNum = 241;BA.debugLine="Escreve_Usuario(sTexto)";
_escreve_usuario(_stexto);
 //BA.debugLineNum = 242;BA.debugLine="Conversa_Bot";
_conversa_bot();
 }else {
 //BA.debugLineNum = 244;BA.debugLine="Log(\"fala\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2655367","fala",0);
 };
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public static String  _ime_heightchanged(int _newheight,int _oldheight) throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Sub IME_HeightChanged(NewHeight As Int, OldHeight";
 //BA.debugLineNum = 217;BA.debugLine="heightTeclado = NewHeight";
_heightteclado = _newheight;
 //BA.debugLineNum = 218;BA.debugLine="pEscrever.SetLayout(pEscrever.Left, heightTeclado";
mostCurrent._pescrever.SetLayout(mostCurrent._pescrever.getLeft(),(int) (_heightteclado-mostCurrent._pescrever.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),mostCurrent._pescrever.getWidth(),mostCurrent._pescrever.getHeight());
 //BA.debugLineNum = 219;BA.debugLine="icEscrever.SetLayout(icEscrever.Left, heightTecla";
mostCurrent._icescrever.SetLayout(mostCurrent._icescrever.getLeft(),(int) (_heightteclado-mostCurrent._icescrever.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)),mostCurrent._icescrever.getWidth(),mostCurrent._icescrever.getHeight());
 //BA.debugLineNum = 220;BA.debugLine="AjustaTamanho_Clv";
_ajustatamanho_clv();
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
geral._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Type mensagemTexto (mensagem As String, assistent";
;
 //BA.debugLineNum = 16;BA.debugLine="Private su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
}
