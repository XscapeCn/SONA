time <- read.table("D:/Desktop/GpuVsCpu.txt",sep = "\t", header=1)

# time$GPU <- log10(time$GPU)
# time$CPU <- log10(time$CPU)

time$GPU <- time$GPU/1000
time$CPU <- time$CPU/1000
time$GPUInPC <- time$GPUInPC/1000
time$CPUInPC <- time$CPUInPC/1000
library("ggplot2")

p <- ggplot(time) + geom_line(aes(x=cols,y=CPU),color = "#1DB9C3",size=2)+
  geom_point(aes(x=cols,y=CPU),color = "#1DB9C3",size = 5)+
  geom_line(aes(x=cols,y=GPU), color = "#7027A0",size=2)+
  geom_point(aes(x=cols,y=GPU), color = "#7027A0",size = 5)+
  geom_line(aes(x=cols,y=CPUInPC),color = "#1DB9C3",size=2, alpha = 0.3)+
  geom_point(aes(x=cols,y=CPUInPC),color = "#1DB9C3",size = 5, alpha = 0.3)+
  geom_line(aes(x=cols,y=GPUInPC), color = "#7027A0",size=2, alpha = 0.3)+
  geom_point(aes(x=cols,y=GPUInPC), color = "#7027A0",size = 5, alpha = 0.3)+
  theme_bw()+
  theme(text = element_text(size = 20))+
  labs(title = "GPU Vs CPU in different samples",x = "Number of gene", y = "Running time (s)")
  # + scale_x_continuous(breaks = time$cols,labels = time$cols)
p



time2 <- read.table("D:/Desktop/CalVsWriting.txt",sep = " ", header=1)


library("ggplot2")

p <- ggplot(time2) + geom_line(aes(x=cols,y=Write),color = "#1DB9C3",size=2)+
  geom_point(aes(x=cols,y=Write),color = "#1DB9C3",size = 5)+
  geom_line(aes(x=cols,y=Calculate), color = "#7027A0",size=2)+
  geom_point(aes(x=cols,y=Calculate), color = "#7027A0",size = 5)+
  theme_bw()+
  theme(text = element_text(size = 20))+
  labs(title = "Calculate Vs Write",x = "Number of gene", y = "Running time (s)")
# + scale_x_continuous(breaks = time$cols,labels = time$cols)
p
