package by.airoports.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import by.airoports.R;

public class AboutCompanyActivity extends Activity {
	
	
	public static Intent buildIntent(Context context){
		return new Intent(context,AboutCompanyActivity.class);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_company);
		
		TextView infoemail = (TextView) findViewById(R.id.infoemail);
		infoemail.setText(Html.fromHtml("<a href=\"info@belavia.by \">info@belavia.by (для получения справочной информации)</a>"));
		infoemail.setMovementMethod(LinkMovementMethod.getInstance());		
		TextView supportEmail = (TextView) findViewById(R.id.supportEmail);
		supportEmail.setText(Html.fromHtml("<a href=\"support@belavia.by\">support@belavia.by (поддержка по вопросам бронирования авиабилетов)</a>"));
		supportEmail.setMovementMethod(LinkMovementMethod.getInstance());
	}

}
