google.maps.__gjsload__('geometry', '\'use strict\';function zn(a,b,c){return Hd(Td(a-b,-180,180))<=c}\nfunction An(a,b,c,d,e){if(!d){d=a.lng();c=Hd(Td(c-d,-180,180));d=a.lng();var f=b.lng();d=Hd(Td(f-d,-180,180));c=c/d;if(!e)return e=l.sin(Vd(a.lat())),e=l.log((1+e)/(1-e))/2,b=l.sin(Vd(b.lat())),b=l.log((1+b)/(1-b))/2,Wd(2*l[fc](l.exp(e+c*(b-e)))-l.PI/2);a=e[qb](a);b=e[qb](b);return e[Nb](new T(a.x+c*(b.x-a.x),a.y+c*(b.y-a.y))).lat()}e=Vd(a.lat());a=Vd(a.lng());d=Vd(b.lat());b=Vd(b.lng());c=Vd(c);return Td(Wd(l[Gc](l.sin(e)*l.cos(d)*l.sin(c-b)-l.sin(d)*l.cos(e)*l.sin(c-a),l.cos(e)*l.cos(d)*l.sin(a-\nb))),-90,90)}\nvar Bn={containsLocation:function(a,b){for(var c=Td(a.lng(),-180,180),d=!!b.get("geodesic"),e=b.get("latLngs"),f=b.get("map"),f=!d&&f?f[Cc]():null,g=!1,h=0,n=e[bc]();h<n;++h)for(var r=e[Rc](h),s=0,u=r[bc]();s<u;++s){var x=r[Rc](s),D=r[Rc]((s+1)%u),I=Td(x.lng(),-180,180),F=Td(D.lng(),-180,180),J=Kd(I,F),I=Ld(I,F);(180<J-I?c>=J||c<I:c<J&&c>=I)&&An(x,D,c,d,f)<a.lat()&&(g=!g)}return g||Bn.isLocationOnEdge(a,b)},isLocationOnEdge:function(a,b,c){c=c||1E-9;var d=Td(a.lng(),-180,180),e=b instanceof jm,f=\n!!b.get("geodesic"),g=b.get("latLngs");b=b.get("map");b=!f&&b?b[Cc]():null;for(var h=0,n=g[bc]();h<n;++h)for(var r=g[Rc](h),s=r[bc](),u=e?s:s-1,x=0;x<u;++x){var D=r[Rc](x),I=r[Rc]((x+1)%s),F=Td(D.lng(),-180,180),J=Td(I.lng(),-180,180),S=Kd(F,J),$=Ld(F,J);if(F=zn(F,J,1E-9)&&(zn(F,d,c)||zn(J,d,c)))var F=a.lat(),J=Ld(D.lat(),I.lat())-c,R=Kd(D.lat(),I.lat())+c,F=F>=J&&F<=R;if(F)return!0;if(180<S-$?d+c>=S||d-c<=$:d+c>=$&&d-c<=S)if(D=An(D,I,d,f,b),Hd(D-a.lat())<c)return!0}return!1}};var Cn={computeHeading:function(a,b){var c=re(a),d=re(b),e=se(b)-se(a);return Td(Wd(l[Gc](l.sin(e)*l.cos(d),l.cos(c)*l.sin(d)-l.sin(c)*l.cos(d)*l.cos(e))),-180,180)},computeOffset:function(a,b,c,d){b/=d||6378137;c=Vd(c);var e=re(a);d=l.cos(b);b=l.sin(b);var f=l.sin(e),e=l.cos(e),g=d*f+b*e*l.cos(c);return new P(Wd(l[pc](g)),Wd(se(a)+l[Gc](b*e*l.sin(c),d-f*g)))},computeOffsetOrigin:function(a,b,c,d){c=Vd(c);b/=d||6378137;d=l.cos(b);var e=l.sin(b)*l.cos(c);b=l.sin(b)*l.sin(c);c=l.sin(re(a));var f=e*\ne*d*d+d*d*d*d-d*d*c*c;if(0>f)return null;var g=e*c+l[Hc](f),g=g/(d*d+e*e),h=(c-e*g)/d,g=l[Gc](h,g);if(g<-l.PI/2||g>l.PI/2)g=e*c-l[Hc](f),g=l[Gc](h,g/(d*d+e*e));return g<-l.PI/2||g>l.PI/2?null:new P(Wd(g),Wd(se(a)-l[Gc](b,d*l.cos(g)-e*l.sin(g))))},interpolate:function(a,b,c){var d=re(a),e=se(a),f=re(b),g=se(b),h=l.cos(d),n=l.cos(f);b=Cn.Xf(a,b);var r=l.sin(b);if(1E-6>r)return new P(a.lat(),a.lng());a=l.sin((1-c)*b)/r;c=l.sin(c*b)/r;b=a*h*l.cos(e)+c*n*l.cos(g);e=a*h*l.sin(e)+c*n*l.sin(g);return new P(Wd(l[Gc](a*\nl.sin(d)+c*l.sin(f),l[Hc](b*b+e*e))),Wd(l[Gc](e,b)))},Xf:function(a,b){var c=re(a),d=re(b);return 2*l[pc](l[Hc](l.pow(l.sin((c-d)/2),2)+l.cos(c)*l.cos(d)*l.pow(l.sin((se(a)-se(b))/2),2)))},computeDistanceBetween:function(a,b,c){return Cn.Xf(a,b)*(c||6378137)},computeLength:function(a,b){var c=b||6378137,d=0;a instanceof Of&&(a=a[hc]());for(var e=0,f=a[G]-1;e<f;++e)d+=Cn.computeDistanceBetween(a[e],a[e+1],c);return d},computeArea:function(a,b){return l.abs(Cn.computeSignedArea(a,b))},computeSignedArea:function(a,\nb){var c=b||6378137;a instanceof Of&&(a=a[hc]());for(var d=a[0],e=0,f=1,g=a[G]-1;f<g;++f)e+=Cn.an(d,a[f],a[f+1]);return e*c*c},an:function(a,b,c){return Cn.Vm(a,b,c)*Cn.Wm(a,b,c)},Vm:function(a,b,c){var d=[a,b,c,a];a=[];for(c=b=0;3>c;++c)a[c]=Cn.Xf(d[c],d[c+1]),b+=a[c];b/=2;d=l.tan(b/2);for(c=0;3>c;++c)d*=l.tan((b-a[c])/2);return 4*l[fc](l[Hc](l.abs(d)))},Wm:function(a,b,c){a=[a,b,c];b=[];for(c=0;3>c;++c){var d=a[c],e=re(d),d=se(d),f=b[c]=[];f[0]=l.cos(e)*l.cos(d);f[1]=l.cos(e)*l.sin(d);f[2]=l.sin(e)}return 0<\nb[0][0]*b[1][1]*b[2][2]+b[1][0]*b[2][1]*b[0][2]+b[2][0]*b[0][1]*b[1][2]-b[0][0]*b[2][1]*b[1][2]-b[1][0]*b[0][1]*b[2][2]-b[2][0]*b[1][1]*b[0][2]?1:-1}};var Dn={decodePath:function(a){for(var b=L(a),c=ea(l[tb](a[G]/2)),d=0,e=0,f=0,g=0;d<b;++g){var h=1,n=0,r;do r=a[$c](d++)-63-1,h+=r<<n,n+=5;while(31<=r);e+=h&1?~(h>>1):h>>1;h=1;n=0;do r=a[$c](d++)-63-1,h+=r<<n,n+=5;while(31<=r);f+=h&1?~(h>>1):h>>1;c[g]=new P(1E-5*e,1E-5*f,!0)}cb(c,g);return c},encodePath:function(a){a instanceof Of&&(a=a[hc]());return Dn.fn(a,function(a){return[Md(1E5*a.lat()),Md(1E5*a.lng())]})},fn:function(a,b){for(var c=[],d=[0,0],e,f=0,g=L(a);f<g;++f)e=b?b(a[f]):a[f],Dn.gi(e[0]-\nd[0],c),Dn.gi(e[1]-d[1],c),d=e;return c[dd]("")},Fn:function(a){for(var b=L(a),c=ea(b),d=0;d<b;++d)c[d]=a[$c](d)-63;return c},gi:function(a,b){return Dn.gn(0>a?~(a<<1):a<<1,b)},gn:function(a,b){for(;32<=a;)b[B](String[Dc]((32|a&31)+63)),a>>=5;b[B](String[Dc](a+63));return b}};Ff.geometry=function(a){eval(a)};Cd.google.maps.geometry={encoding:Dn,spherical:Cn,poly:Bn};function En(){}K=En[H];K.decodePath=Dn.decodePath;K.encodePath=Dn.encodePath;K.computeDistanceBetween=Cn.computeDistanceBetween;K.interpolate=Cn.interpolate;K.computeHeading=Cn[zc];K.computeOffset=Cn.computeOffset;K.computeOffsetOrigin=Cn.computeOffsetOrigin;If("geometry",new En);\n')