strict digraph G {
  vpc3 [ label="vpc3
10.0.3.0/24" ];
  vpc2 [ label="vpc2
10.0.2.0/24" ];
  vpc1 [ label="vpc1
10.0.1.0/24" ];
  vpc3 -> vpc1;
  vpc3 -> vpc2;
  vpc2 -> vpc1;
  vpc2 -> vpc3;
  vpc1 -> vpc2;
  vpc1 -> vpc3;
}
