import { Link } from 'react-router-dom'
import type { Build } from '../../types'

interface Props {
  build: Build
}

export default function BuildCard({ build }: Props) {
  return (
    <Link
      to={`/builds/${build.slug}`}
      className="block bg-souls-stone border border-souls-gold/20 rounded-lg p-4 hover:border-souls-gold/50 transition-colors"
    >
      <div className="flex items-start justify-between mb-2">
        <h3 className="text-souls-gold font-semibold">{build.title}</h3>
        <span className="text-xs text-souls-ash ml-2 shrink-0">Lv {build.level}</span>
      </div>

      <p className="text-xs text-souls-ash mb-3">
        {build.game.name} &bull; {build.gameClass.name}
      </p>

      {build.description && (
        <p className="text-sm text-stone-400 line-clamp-2 mb-3">{build.description}</p>
      )}

      <div className="flex items-center justify-between text-xs text-souls-ash">
        <span>by {build.user.username}</span>
        <span>&#9650; {build.upvoteCount}</span>
      </div>
    </Link>
  )
}
