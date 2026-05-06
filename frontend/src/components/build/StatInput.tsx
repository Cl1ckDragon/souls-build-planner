interface Props {
  label: string
  value: number
  base: number
  onChange: (v: number) => void
  disabled?: boolean
}

export default function StatInput({ label, value, base, onChange, disabled }: Props) {
  const spent = value - base

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const parsed = parseInt(e.target.value)
    if (isNaN(parsed)) return
    onChange(Math.max(base, Math.min(99, parsed)))
  }

  return (
    <div className="flex items-center gap-3 py-1">
      <span className="w-24 text-sm text-stone-300 shrink-0">{label}</span>
      <span className="w-8 text-xs text-souls-ash text-right shrink-0" title="Base stat">
        {base}
      </span>
      <input
        type="number"
        min={base}
        max={99}
        value={value}
        onChange={handleChange}
        disabled={disabled}
        className="w-16 bg-souls-dark border border-souls-gold/30 rounded px-2 py-1 text-center text-stone-200
                   focus:outline-none focus:border-souls-gold disabled:opacity-40 [appearance:textfield]
                   [&::-webkit-inner-spin-button]:appearance-none [&::-webkit-outer-spin-button]:appearance-none"
      />
      <span className={`text-xs w-8 ${spent > 0 ? 'text-souls-gold' : 'text-transparent'}`}>
        +{spent}
      </span>
    </div>
  )
}
