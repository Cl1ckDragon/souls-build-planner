/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        souls: {
          gold:  '#c8a84b',
          dark:  '#0f0e0c',
          stone: '#2a2620',
          ash:   '#6b6560',
        },
      },
      fontFamily: {
        serif: ['"IM Fell English"', 'Georgia', 'serif'],
      },
    },
  },
  plugins: [],
}
