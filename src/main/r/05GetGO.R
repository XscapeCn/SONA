p <- read.table("D:/Desktop/test/L_Apvalues_matrix.txt")
p <- -log10(p)
which(p == Inf, arr.ind = T)

which(p <= 50,arr.ind = T)

leaf <- read.table("D:/Desktop/S4LeafGeneInModule.txt")
awn <- read.table("D:/Desktop/S4AwnGeneInModule.txt")

expleaf <- read.table("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Leaf.txt", row.names = 4)%>% .[,-c(1:3)]

sumleaf <- as.character(sumleaf)
expawn <- read.table("D:/Desktop/ScriptsInNetwork/Data/Expression/S4Awn.txt", row.names = 4) %>%  .[,-c(1:3)]
sumawn <- sumawn[,4] %>%  as.character() 
cleaf <- rownames(subset(leaf, bwnetModuleLabels == 15))
cawn <- rownames(subset(awn, bwnetModuleLabels == 19))
and <- intersect(cleaf, cawn)


#no expression in leaf: 3
# TraesCS2D02G500700,TraesCS5A02G404300, TraesCS7D02G142700
diffawm <- setdiff(cawn, and)

length(setdiff(diffawm, sumleaf))

#no expression in leaf: 1
# TraesCS1B02G409800
diffleaf <- setdiff(cleaf, and)
length(setdiff(diffleaf, sumawn))

#leaf-specific gene exp
diffexpleafleaf <- expleaf[diffleaf,]
diffexpleafawn <- expawn[diffleaf,]
avrleafleaf <- as.numeric(rowMeans(diffexpleafleaf)) %>%  as.data.frame()
avrleafleaf$Tissue <- "Leaf"
avrleafawn <- as.numeric(rowMeans(diffexpleafawn)) %>%  as.data.frame()
avrleafawn$Tissue <- "Awn"
lla <- rbind(avrleafawn, avrleafleaf)
ggplot(lla) + geom_boxplot(aes(x = Tissue, y=log2(.), fill = "#ff805b"))+
  theme_bw()+
  labs(title = "Leaf-specific gene exp", y = "Log(Exp)", x="")+
  theme(text = element_text(size = 20))
ggsave("leaf-specific gene exp.png", height = 8,width =6, dpi =300)

#awn-specific gene exp
diffexpawnleaf <- expleaf[diffawm,]
diffexpawnawn <- expawn[diffawm,]
avrawnleaf <- as.numeric(rowMeans(diffexpawnleaf)) %>%  as.data.frame()
avrawnleaf$Tissue <- "Leaf"
avrawnawn <- as.numeric(rowMeans(diffexpawnawn)) %>%  as.data.frame()
avrawnawn$Tissue <- "Awn"
ala <- rbind(avrawnawn, avrawnleaf)
ggplot(ala) + geom_boxplot(aes(x = Tissue, y=log2(.), fill = "#ff805b"))+
  theme_bw()+
  labs(title = "Awn-specific gene exp", y = "Log(Exp)", x="")+
  theme(text = element_text(size = 20))
ggsave("Awn-specific gene exp.png", height = 8,width =6, dpi =300)


avrawn <- as.numeric(rowMeans(expawn)) %>%  as.data.frame()
avrawn$Tissue <- "Awn"
avrleaf <- as.numeric(rowMeans(expleaf)) %>%  as.data.frame()
avrleaf$Tissue <- "leaf"
al <- rbind(avrawn, avrleaf)
ggplot(al) + geom_boxplot(aes(x = Tissue, y=log10(.), fill = "#ff805b"))+
  theme_bw()+
  labs(title = "Avr gene exp", y = "Log(Exp)", x="")+
  theme(text = element_text(size = 20))
ggsave("gene exp.png", height = 8,width =6, dpi =300)


length(cleaf)
length(cawn)
length(and)

# ###
# row col
# row0    1   1
# row5    6   6
# row10  11   7
# row1    2  13
# row1    2  15
# row15  16  20
# ###