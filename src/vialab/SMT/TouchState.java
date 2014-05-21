package vialab.SMT;

//standard library imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

//libTuio imports
import TUIO.*;

/**
 * A Touch "Container" class
 */
class TouchState implements Iterable<Touch> {
	protected LinkedHashMap<Long, Touch> idToTouches =
		new LinkedHashMap<Long, Touch>();

	public TouchState(){}

	public TouchState( TouchState state){
		if( state == null) return;
		for( Touch touch : state)
			add(touch);
	}

	public void add( Touch touch){
		if( touch == null) return;
		idToTouches.put( touch.getSessionID(), touch);
	}

	public Touch remove( Long id){
		if( idToTouches.containsKey( id))
			return idToTouches.remove( id);
		return null;
	}

	public Touch getById( long id){
		return idToTouches.get( id);
	}

	public Touch get( int index){
		int i = 0;
		for( Touch touch : idToTouches.values()){
			if( i == index)
				return touch;
			i++;
		}
		return null;
	}

	public int size(){
		return idToTouches.size();
	}
	public boolean contains( Long id){
		return idToTouches.containsKey( id);
	}

	@Override
	public Iterator<Touch> iterator(){
		return idToTouches.values().iterator();
	}

	public void update( List<TuioCursor> currentTuioState){
		ArrayList<Touch> currentCursors = new ArrayList<Touch>();
		for( TuioCursor cursor : currentTuioState){
			Touch touch;
			if( ! this.contains( cursor.getSessionID())){
				// if no touch maps to it, create touch from it, add to map
				touch = new Touch( cursor);
				this.add( touch);
				touch.isDown = true;
			}
			else {
				// otherwise find corresponding touch and update the touch
				touch = this.getById( cursor.getSessionID());
				touch.update( cursor);
			}
			currentCursors.add(touch);
		}

		// remove touches that correspond to removed cursors
		Iterator<Touch> rmv = iterator();
		while (rmv.hasNext()){
			Touch touch = rmv.next();
			if ( ! currentCursors.contains( touch)){
				// the cursor was not updated, assume up and remove
				rmv.remove();
				touch.isDown = false;
			}
		}
	}
}
