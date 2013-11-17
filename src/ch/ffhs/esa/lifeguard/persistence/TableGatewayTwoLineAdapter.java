package ch.ffhs.esa.lifeguard.persistence;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Generic two line list adapter for table gateways to work with
 * android.R.layout.two_line_list_item while using data in collections.
 *
 * @see <a href="http://stackoverflow.com/a/9814071">An abstract TwoLineArrayAdapter</a>
 * 
 * @author unascribed
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public abstract class TableGatewayTwoLineAdapter<T> extends ArrayAdapter<T> {
	
	/*//////////////////////////////////////////////////////////////////////////
	 * INITIALIZATION
	 */
	
	/**
	 * Creates an adapter object using Android's standard two line list item
	 * layout.
	 * 
	 * @param context The context
	 * @param objects The objects to render within the list view
	 */
	public TableGatewayTwoLineAdapter(Context context, List<T> objects) {
		super(context, android.R.layout.two_line_list_item, objects);
	}
	
	
	/*//////////////////////////////////////////////////////////////////////////
	 * PUBLIC INTERFACE
	 */
	
	/**
	 * Assembles the view
	 */
	@Override
	public android.view.View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItemView = convertView;
		
		if (null == listItemView) {
			listItemView = inflater.inflate(
				android.R.layout.two_line_list_item,
				parent,
				false
			);
		}
		
		TextView lineOneView = (TextView)listItemView.findViewById(android.R.id.text1);
		TextView lineTwoView = (TextView)listItemView.findViewById(android.R.id.text2);
		
		T item = (T)this.getItem(position);
		lineOneView.setText(this.getLineOneText(item));
		lineTwoView.setText(this.getLineTwoText(item));
		
		return listItemView;
	}
	
	/**
	 * Retrieves the text to be used with the list item's first line
	 * 
	 * @param object The object to retrieve the text from
	 * @return The text
	 */
	public abstract String getLineOneText(T object);
	
	/**
	 * Retrieves the text to be used with the list item's second line
	 * 
	 * @param object The object to retrieve the text from
	 * @return The text
	 */
	public abstract String getLineTwoText(T object);
}
