import { Link } from 'react-router-dom'

export default function HomePage() {
  return (
    <div className="flex flex-col items-center justify-center py-24 text-center">
      <h1 className="font-serif text-4xl text-souls-gold mb-4 tracking-wide">
        Souls Build Planner
      </h1>
      <p className="text-stone-400 max-w-md mb-10 leading-relaxed">
        Craft, share, and discover character builds for Elden Ring and the Dark Souls trilogy.
        Theory-craft the perfect stat spread before you commit your rune echoes.
      </p>
      <div className="flex gap-4">
        <Link
          to="/builds"
          className="px-6 py-2 border border-souls-gold/50 text-souls-gold rounded hover:bg-souls-gold/10 transition-colors"
        >
          Browse Builds
        </Link>
        <Link
          to="/register"
          className="px-6 py-2 bg-souls-gold/20 border border-souls-gold text-souls-gold rounded hover:bg-souls-gold/30 transition-colors"
        >
          Create an Account
        </Link>
      </div>
    </div>
  )
}
