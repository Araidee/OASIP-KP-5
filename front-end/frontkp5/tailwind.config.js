module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}"
  ],
  daisyui: {
    themes: [
      {
        mytheme: {
        
"primary": "#6ee7b7",
        
"secondary": "#f3f4f6",
        
"accent": "#1FB2A6",
        
"neutral": "#191D24",
        
"base-100": "#2A303C",
        
"info": "#3ABFF8",
        
"success": "#36D399",
        
"warning": "#FBBD23",
        
"error": "#F87272",
        },
      },
    ],
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: ["garden"]
  }
}
