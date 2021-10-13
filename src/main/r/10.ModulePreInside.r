#!/usr/bin/Rscript
library(WGCNA)

allowWGCNAThreads(nThreads = 32)
load("/data1/home/songxu/task/WGCNA/result/ModuleClusterGrain/.RData")
multiExpr = list(s6 = list(data = s6), s7= list(data = s7),s8= list(data = s8), s9=list(data =s9))
multiColor = list(s6 = bwnetModuleColors6)

system.time( {mp = modulePreservation(multiExpr, multiColor,referenceNetworks = 1,nPermutations = 100,randomSeed = 1,quickCor = 0,verbose = 3)} )
save.image(file = "s6between789.RData")




#!/usr/bin/Rscript
library(WGCNA)
allowWGCNAThreads(nThreads = 32)
load("/data1/home/songxu/task/WGCNA/result/ModuleClusterGrain/.RData")

t1 = sample(dim(s6)[2], size = floor(dim(s6)[2]*0.6), replace = FALSE)
t2 = sample(dim(s6)[2], size = floor(dim(s6)[2]*0.6), replace = FALSE)
t3 = sample(dim(s6)[2], size = floor(dim(s6)[2]*0.6), replace = FALSE)

multiExpr = list(s6 = list(data = s6), t1= list(data = s6[,t1]),t2= list(data = s6[,t2]), t3=list(data =s6[,t3]))
multiColor = list(s6 = bwnetModuleColors6)

system.time( {mp = modulePreservation(multiExpr, multiColor,referenceNetworks = 1,nPermutations = 50,randomSeed = 1,quickCor = 0,verbose = 3)} )

save.image(file = "s6selfPreservation.RData")



stats$module = rownames(stats)
library(ggplot2)
library(ggrepel)
g=ggplot(data=stats,aes(x=moduleSize,y=Zsummary.pres,col=module))+
  geom_point(alpha=0.8, size=5) +
  theme_bw(base_size=15)+
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        axis.line = element_line(colour = "black"))+
  xlab("ModuleSize") + ylab("Zsummary.pres") +
  ggtitle( "Preservation Zsummary" ) +
  theme(plot.title = element_text(size=15,hjust = 0.5))+
  scale_colour_manual(values = c(stats$module))+
  ## 去掉图注
  theme(legend.position='none')+
  ## 添加阈值线
  geom_hline(yintercept = c(2,10),lty=4,lwd=1,col=c("blue","red"))+
  ## 添加文本信息
  geom_text_repel(aes(label=module),color="black",alpha = 0.8)
print(g)
ggsave(g,filename = "PZ_test.pdf",height = 8,width = 6)
dev.off()