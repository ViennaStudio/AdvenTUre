<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.9" tiledversion="1.9.1" name="Water" tilewidth="16" tileheight="16" tilecount="20" columns="5">
 <image source="water-assets/Water16x16.png" width="80" height="64"/>
 <tile id="14">
  <animation>
   <frame tileid="14" duration="600"/>
   <frame tileid="6" duration="900"/>
  </animation>
 </tile>
 <tile id="16">
  <animation>
   <frame tileid="15" duration="600"/>
   <frame tileid="16" duration="600"/>
  </animation>
 </tile>
 <wangsets>
  <wangset name="Water" type="mixed" tile="-1">
   <wangcolor name="Water" color="#ff0000" tile="-1" probability="1"/>
   <wangtile tileid="0" wangid="1,1,0,0,0,1,1,1"/>
   <wangtile tileid="1" wangid="1,1,0,0,0,0,0,1"/>
   <wangtile tileid="2" wangid="1,1,1,1,0,0,0,1"/>
   <wangtile tileid="3" wangid="0,0,0,1,0,0,0,0"/>
   <wangtile tileid="4" wangid="0,0,0,0,0,1,0,0"/>
   <wangtile tileid="5" wangid="0,0,0,0,0,1,1,1"/>
   <wangtile tileid="7" wangid="0,1,1,1,0,0,0,0"/>
   <wangtile tileid="8" wangid="0,1,0,0,0,0,0,0"/>
   <wangtile tileid="9" wangid="0,0,0,0,0,0,0,1"/>
   <wangtile tileid="10" wangid="0,0,0,1,1,1,1,1"/>
   <wangtile tileid="11" wangid="0,0,0,1,1,1,0,0"/>
   <wangtile tileid="12" wangid="0,1,1,1,1,1,0,0"/>
  </wangset>
 </wangsets>
</tileset>
