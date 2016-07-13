

findarray = [];
replacearray = [];
labelarray = [];
otherarrays = []; //futureuse
stripextract = false;
replaceextract = false;
delimout="|";
list = [
["[\\d]{1,6}(?!\\d)","label3"],
["([0-9\\.\\/]{1,6}) ?(x|X) ?([0-9\\.\\/]{1,6}) ?(x|X) ?([0-9\\.\\/]{1,6}) ?(?i)(mm|cm|in|gm|ml|m|\\\"| \\\"|yd)","label4"],
["([0-9\\.\\/]{1,6}) ?(x|X) ?([0-9\\.\\/]{1,6}) ?(?i)(mm|cm|in|gm|ml|m|\\\"| \\\"|yd)","label5"],
["([0-9]{1,6}-? ?[0-9\\.\\/]{0,6}) ?(x|X) ?([0-9]{1,6}-? ?[0-9\\.\\/]{0,6}) ?(x|X) ?([0-9]{1,6}-? ?[0-9\\.\\/]{0,6}) ?(?i)(mm|cm|in|gm|ml|m|\\\"| \\\"|yd)","label6"],
["([0-9]{1,6}-? ?[0-9\\.\\/]{0,6}) ?(x|X) ?([0-9]{1,6}-? ?[0-9\\.\\/]{0,6}) ?(?i)(mm|cm|in|gm|ml|m|\"| \"|yd)","label7"]];
input1Arr = ["RES 0603 1% 169 OHM 3W",
"RES 0603 3.5W 1% 169 OHM",
"RES 0805 0 OHM 1/4 W 2%",
"resistor 2% 0Ohm 1/4w 0805",
"RESS , 1206 TF CH , 0.0 OHM , 1 / 8 W , 5%",
"RESS 825 OHM 1/8 W 1%",
"RESS 825 OHM 1/8 W 1%",
"AXIAL,HERM SLD,.12UF,100V,10%,A CASE,P LVL,MRK'D",
"0805,X7R,.47UF,5%,16V,TR,HAZMAT",
"RADIAL,MOLDED,X7R,.15UF,50V,10%,R-LVL,BULK,STDOFF",
"LOWPROFILE,150UF,10V,10%,Y CASE,TR,HAZMAT",
"AXIAL,HERM SLD,3.9UF,50V,10%,B CASE,C LVL,MRK'D",
"1206,X7R,.015UF,5%,50V,TR,HAZMAT",
"RADIAL, B CSE 10UF,25V,20%,.10LS,STRAIGHT,T/R]"];
input1Arr.each{
	input1=it;
numRefRecords = list.size()
numRefRecords.times {recordNumber ->
  listRow = list[recordNumber];
  listRowNumCols = listRow.size();
  listRowNumCols.times {colNumber ->
    if (colNumber == 0){
      findarray << listRow[0].toString();
    }else{
      if(listRow[colNumber]){
        labelarray << listRow[colNumber].toString();
      }else{
        labelarray << ""
      }
    }
  }
}


  output1 = input1.toString().toUpperCase()
  output2 = ""
  output3 = ""

  if (output1) {
    for (int i = 0;i < findarray.size(); i++) {
      element = findarray[i]
      search = /($element)/
      match = output1 =~ search
      println("Searching input " + input1 + " with regex " + element)
      if (!stripextract) {
        output1 = input1;
      }else{
        output1 = match.replaceAll("");
      }
      
      if (match.hasGroup() && match.size() > 0){
      	println ("Has " + match.groupCount() + " groups");
        for (int j in 0..<match.groupCount()){
        	println("group " + j + " has " + match.size() + " matches");
        	if (match[j]){println(match[j]);}
          for (int k in 0..<match.count){
              if (output2) {
                   output2 = output2 + delimout + match[j][k];
                } else {
                   output2 = match[j][k];
                }
                
               if(!replaceextract){
                  if (output3){
                    output3 = output3 + delimout + match[j][k] + "["+labelarray[i]+"]";
                  }else{
                    output3 = match[j][k] + "["+labelarray[i]+"]";
                  }
              }else{
                output3 = match.replaceAll(labelarray[i]);
              }
            }
          }
      
      }else{
        for (int k in 0..<match.count){
        	println("Match w/out group " + k + " has " + match.count + " matches");
          if (output2) {
               output2 = output2 + delimout + match[k];
            } else {
               output2 = match[k];
            }
            
           if(!replaceextract){
              if (output3){
                output3 = output3 + delimout + match[k] + "["+labelarray[i]+"]";
              }else{
                output3 = match[k] + "["+labelarray[i]+"]";
              }
          }else{
            output3 = match.replaceAll(labelarray[i]);
          }
        }
      
      }
  }
}
}
println ("Desc is" + output1);
//println ("Extr is" + output2);
//println ("Repl is" + output3);
