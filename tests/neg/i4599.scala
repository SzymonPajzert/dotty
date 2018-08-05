object Test extends App {
  var i = 0;
  
  def failWhile() = {
    while i < 5 do { i += 1; println(i) }
  }
  
  def failWhileDoNewline() = {
    while i < 5
    do { i += 1; println(i) }
  }
  
  def succeedWhileParenthesis() = {
    while (i < 5) {
      i += 1; println(i)
    }
    
    while (i < 10) i += 1
  }
  
  def ifThenElseFails() = {
    if i = 0
    then 1
    else 0
  }
  
  /*
  def failDoWhile() = {
    do i += 1
    while i < 5  // error: parse
  }
  
  def failForYield() = {
    for i <- 1 to 10  // error: parse
    yield i + 1
  }
  
  def failForDo() = {
    for i <- 1 to 10 // error: parse
    do println(i)
  }*/
}