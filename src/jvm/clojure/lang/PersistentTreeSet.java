/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
 *   which can be found in the file CPL.TXT at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

/* rich Mar 3, 2008 */

package clojure.lang;

import java.util.List;
import java.util.Iterator;

public class PersistentTreeSet extends APersistentSet implements Reversible{
static public final PersistentTreeSet EMPTY = new PersistentTreeSet(null, PersistentTreeMap.EMPTY);

public static PersistentTreeSet create(Object... init){
	PersistentTreeSet ret = EMPTY;
	for(int i = 0; i < init.length; i++)
		{
		ret = (PersistentTreeSet) ret.cons(init[i]);
		}
	return ret;
}

public static PersistentTreeSet create(List init){
	PersistentTreeSet ret = EMPTY;
	for(Iterator i = init.iterator(); i.hasNext();)
		{
		Object key = i.next();
		ret = (PersistentTreeSet) ret.cons(key);
		}
	return ret;
}

static public PersistentTreeSet create(ISeq items){
	PersistentTreeSet ret = EMPTY;
	for(; items != null; items = items.rest())
		{
		ret = (PersistentTreeSet) ret.cons(items.first());
		}
	return ret;
}

PersistentTreeSet(IPersistentMap meta, IPersistentMap impl){
	super(meta, impl);
}

public IPersistentSet disjoin(Object key) throws Exception{
	if(contains(key))
		return new PersistentTreeSet(meta(),impl.without(key));
	return this;
}

public IPersistentSet cons(Object o){
	if(contains(o))
		return this;
	return new PersistentTreeSet(meta(),impl.assoc(o,o));
}

public ISeq rseq() throws Exception{
	return APersistentMap.KeySeq.create(((Reversible) impl).rseq());
}

public PersistentTreeSet withMeta(IPersistentMap meta){
	return new PersistentTreeSet(meta, impl);
}
}