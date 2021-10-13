library("ggplot2")


df <- read.table("D:/Desktop/untitled text 10.txt", header = 1)

ggplot(df, aes(GS,bio5)) + geom_point(size = 5, alpha = 0.5 ,color = "#00ADB5")+geom_smooth(method=lm, se=FALSE, color = "#F29191", size = 2)+
  theme_bw() + 
  labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank())




dff <- read.table("D:/Desktop/TE-GS.txt", header = 1)

ggplot(dff, aes(GS,RLC)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#890596" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/RLC.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$RLC) #0.5825 0.0288


ggplot(dff, aes(GS,RLG)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#1CC5DC" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/RLG.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$RLG) #0.4002 0.156


ggplot(dff, aes(GS,DTC)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#CF0000" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/DTC.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$DTC) #0.2177 0.455


ggplot(dff, aes(GS,RST)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#9EDE73" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/RST.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$RST) # 0.6169 0.0188

ggplot(dff, aes(GS,RIL)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#51C4D3" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/RIL.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$RIL) # 0.4190 0.1359

ggplot(dff, aes(GS,DTX)) + geom_point(size = 5, alpha = 0.5 ,color = "grey")+geom_smooth(method=lm, se=FALSE, color ="#3D84B8" , size = 2)+
  theme_bw() + 
  # labs(title = "Correlation between GS and Bio5")+
  theme(text = element_text(size = 20))+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank()) 
ggsave("D:/Desktop/DTX.pdf", width = 5, height = 5, dpi =300)
cor.test(dff$GS, dff$DTX) # 0.2666 0.3569
