HashtableUtil = Class.create();

HashtableUtil._hash = Object;

HashtableUtil.add = function(key,value){
	if(typeof(key)!="undefined")
	{
		if(HashtableUtil.contains(key)==false)
		{
			HashtableUtil._hash[key]=typeof(value)=="undefined"?null:value;
			return true;
		}
		else 
		{
			return false;
		}
	} 
	else 
	{
		return false;
	}
}
HashtableUtil.remove = function(key){
	delete this._hash[key];
}

HashtableUtil.count = function(){
	var i=0;
	for(var k in this._hash){
		i++;
	} 
	return i;
}

HashtableUtil.items = function(key){
	return this._hash[key];
}

HashtableUtil.contains = function(key){ 
	return typeof(this._hash[key])!="undefined";
}

HashtableUtil.clear = function(){
	for(var k in this._hash){
		delete this._hash[k];
	}
}

