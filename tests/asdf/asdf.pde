import vialab.SMT.*;

Zone parent, one, two;

void setup(){
	size( 800, 600, SMT.RENDERER);
	SMT.init( this);

	println( SMT.revision);
	println( SMT.version);
	println( SMT.version_pretty);
	println( SMT.getRevision());
	println( SMT.getVersion());
	println( SMT.getPrettyVersion());
}

void draw(){
	background( 20);
}
