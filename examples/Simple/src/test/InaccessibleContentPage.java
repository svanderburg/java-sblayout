package test;
import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.page.content.*;

public class InaccessibleContentPage extends ContentPage
{
	public InaccessibleContentPage(String title, Contents contents)
	{
		super(title, contents);
	}
	
	@Override
	public boolean checkAccessibility()
	{
		return false;
	}
}
