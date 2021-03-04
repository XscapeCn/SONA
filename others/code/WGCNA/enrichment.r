hub <- AnnotationHub::AnnotationHub()

for (color in large1000) {
  go_term <- transform(color, 'go_id')
  enrich <- enrich_self(go_term[,2], 'BP')
  dotplot <- dotplot(enrich)
  barplot <- barplot(enrich)
  name_dot <- paste(color, '_dotplot.png')
  name_bar <- paste(color, '_barplot.png')
  draw(name_dot, dotplot)
  draw(name_bar, barplot)
}